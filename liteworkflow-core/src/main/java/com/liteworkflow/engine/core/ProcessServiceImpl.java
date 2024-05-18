package com.liteworkflow.engine.core;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessService;
import com.liteworkflow.engine.SnakerException;
import com.liteworkflow.engine.cache.Cache;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cache.CacheManagerAware;
import com.liteworkflow.engine.entity.HistoryOrder;
import com.liteworkflow.engine.entity.Process;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.parser.ModelParser;
import com.liteworkflow.engine.request.HistoryOrderPageRequest;
import com.liteworkflow.engine.request.ProcessPageRequest;
import com.liteworkflow.engine.service.HistoryOrderEntityService;
import com.liteworkflow.engine.service.ProcessEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @author yuqs
 * @since 1.0
 */
public class ProcessServiceImpl extends AccessService implements ProcessService, CacheManagerAware
{
	private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);

	private static final String DEFAULT_SEPARATOR = ".";

	/**
	 * 流程定义对象cache名称
	 */
	private static final String CACHE_ENTITY = "snaker.process.entity";

	/**
	 * 流程id、name的cache名称
	 */
	private static final String CACHE_NAME = "snaker.process.name";

	/**
	 * cache manager
	 */
	private CacheManager cacheManager;

	/**
	 * 实体cache(key=name,value=entity对象)
	 */
	private Cache<String, Process> entityCache;

	/**
	 * 名称cache(key=id,value=name对象)
	 */
	private Cache<String, String> nameCache;

	private ProcessEntityService processEntityService;

	private HistoryOrderEntityService historyOrderEntityService;

	public void check(Process process, String idOrName)
	{
		AssertHelper.notNull(process, "指定的流程定义[id/name=" + idOrName + "]不存在");
		if (process.getState() != null && process.getState() == 0)
		{
			throw new IllegalArgumentException("指定的流程定义[id/name=" + idOrName + ",version=" + process.getVersion() + "]为非活动状态");
		}
	}

	/**
	 * 保存process实体对象
	 */
	public void saveProcess(Process process)
	{
		processEntityService.save(process);
	}

	/**
	 * 更新process的类别
	 */
	public void updateType(String id, String type)
	{
		Process entity = getProcessById(id);
		entity.setType(type);
		processEntityService.updateProcessType(id, type);
		cache(entity);
	}

	/**
	 * 根据id获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessById(String id)
	{
		AssertHelper.notEmpty(id);
		Process entity = null;
		String processName;
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if (nameCache != null && entityCache != null)
		{
			processName = nameCache.get(id);
			if (StringHelper.isNotEmpty(processName))
			{
				entity = entityCache.get(processName);
			}
		}
		if (entity != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("obtain process[id={}] from cache.", id);
			}
			return entity;
		}
		entity = processEntityService.getProcess(id);
		if (entity != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("obtain process[id={}] from database.", id);
			}
			cache(entity);
		}
		return entity;
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessByName(String name)
	{
		return getProcessByVersion(name, null);
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessByVersion(String name, Integer version)
	{
		AssertHelper.notEmpty(name);
		if (version == null)
		{
			version = processEntityService.getLatestProcessVersion(name);
		}
		if (version == null)
		{
			version = 0;
		}
		Process entity = null;
		String processName = name + DEFAULT_SEPARATOR + version;
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if (entityCache != null)
		{
			entity = entityCache.get(processName);
		}
		if (entity != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("obtain process[name={}] from cache.", processName);
			}
			return entity;
		}

		ProcessPageRequest request = new ProcessPageRequest();
		request.setNames(new String[] { name });
		request.setVersion(version);
		List<Process> processs = processEntityService.queryList(request);
		if (processs != null && !processs.isEmpty())
		{
			if (log.isDebugEnabled())
			{
				log.debug("obtain process[name={}] from database.", processName);
			}
			entity = processs.get(0);
			cache(entity);
		}
		return entity;
	}

	/**
	 * 根据流程定义xml的输入流解析为字节数组，保存至数据库中，并且put到缓存中
	 * 
	 * @param input 定义输入流
	 */
	public String deploy(InputStream input)
	{
		return deploy(input, null);
	}

	/**
	 * 根据流程定义xml的输入流解析为字节数组，保存至数据库中，并且put到缓存中
	 * 
	 * @param input 定义输入流
	 * @param creator 创建人
	 */
	public String deploy(InputStream input, String creator)
	{
		AssertHelper.notNull(input);
		try
		{
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = ModelParser.parse(bytes);
			Integer version = processEntityService.getLatestProcessVersion(model.getName());
			Process entity = new Process();
			entity.setId(StringHelper.getPrimaryKey());
			if (version == null || version < 0)
			{
				entity.setVersion(0);
			}
			else
			{
				entity.setVersion(version + 1);
			}
			entity.setState(Constants.STATE_ACTIVE);
			entity.setModel(model);
			entity.setBytes(bytes);
			entity.setCreateTime(DateHelper.getTime());
			entity.setCreator(creator);
			saveProcess(entity);
			cache(entity);
			return entity.getId();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据流程定义id、xml的输入流解析为字节数组，保存至数据库中，并且重新put到缓存中
	 * 
	 * @param input 定义输入流
	 */
	public void redeploy(String id, InputStream input)
	{
		AssertHelper.notNull(input);
		Process entity = processEntityService.getProcess(id);
		AssertHelper.notNull(entity);
		try
		{
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = ModelParser.parse(bytes);
			String oldProcessName = entity.getName();
			entity.setModel(model);
			entity.setBytes(bytes);
			processEntityService.update(entity);
			if (!oldProcessName.equalsIgnoreCase(entity.getName()))
			{
				Cache<String, Process> entityCache = ensureAvailableEntityCache();
				if (entityCache != null)
				{
					entityCache.remove(oldProcessName + DEFAULT_SEPARATOR + entity.getVersion());
				}
			}
			cache(entity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据processId卸载流程
	 */
	public void undeploy(String id)
	{
		Process entity = processEntityService.getProcess(id);
		entity.setState(Constants.STATE_FINISH);
		processEntityService.update(entity);
		cache(entity);
	}

	/**
	 * 级联删除指定流程定义的所有数据
	 */
	public void cascadeRemove(String id)
	{
		Process entity = processEntityService.getProcess(id);

		HistoryOrderPageRequest request = new HistoryOrderPageRequest();
		request.setProcessId(id);
		List<HistoryOrder> historyOrders = historyOrderEntityService.queryList(request);

		for (HistoryOrder historyOrder : historyOrders)
		{
			ServiceContext.getEngine().order().cascadeRemove(historyOrder.getId());
		}
		processEntityService.delete(entity);
		clear(entity);
	}

	/**
	 * 查询流程定义
	 */
	public List<Process> getProcesss(ProcessPageRequest request)
	{
		return processEntityService.queryList(request);
	}

	/**
	 * 分页查询流程定义
	 */
	public Page<Process> queryPageData(ProcessPageRequest request)
	{
		return processEntityService.queryPageData(request);
	}

	/**
	 * 缓存实体
	 * 
	 * @param entity 流程定义对象
	 */
	private void cache(Process entity)
	{
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if (entity.getModel() == null && entity.getDBContent() != null)
		{
			entity.setModel(ModelParser.parse(entity.getDBContent()));
		}
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if (nameCache != null && entityCache != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("cache process id is[{}],name is[{}]", entity.getId(), processName);
			}
			entityCache.put(processName, entity);
			nameCache.put(entity.getId(), processName);
		}
		else
		{
			if (log.isDebugEnabled())
			{
				log.debug("no cache implementation class");
			}
		}
	}

	/**
	 * 清除实体
	 * 
	 * @param entity 流程定义对象
	 */
	private void clear(Process entity)
	{
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if (nameCache != null && entityCache != null)
		{
			nameCache.remove(entity.getId());
			entityCache.remove(processName);
		}
	}

	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	private Cache<String, Process> ensureAvailableEntityCache()
	{
		Cache<String, Process> entityCache = ensureEntityCache();
		if (entityCache == null && this.cacheManager != null)
		{
			entityCache = this.cacheManager.getCache(CACHE_ENTITY);
		}
		return entityCache;
	}

	private Cache<String, String> ensureAvailableNameCache()
	{
		Cache<String, String> nameCache = ensureNameCache();
		if (nameCache == null && this.cacheManager != null)
		{
			nameCache = this.cacheManager.getCache(CACHE_NAME);
		}
		return nameCache;
	}

	public Cache<String, Process> ensureEntityCache()
	{
		return entityCache;
	}

	public void setEntityCache(Cache<String, Process> entityCache)
	{
		this.entityCache = entityCache;
	}

	public Cache<String, String> ensureNameCache()
	{
		return nameCache;
	}

	public void setNameCache(Cache<String, String> nameCache)
	{
		this.nameCache = nameCache;
	}

	/**
	 * 设置processEntityService
	 * 
	 * @param processEntityService
	 */
	public void setProcessEntityService(ProcessEntityService processEntityService)
	{
		this.processEntityService = processEntityService;
	}

	/**
	 * 设置historyOrderEntityService
	 * 
	 * @param historyOrderEntityService
	 */
	public void setHistoryOrderEntityService(HistoryOrderEntityService historyOrderEntityService)
	{
		this.historyOrderEntityService = historyOrderEntityService;
	}
}
