package com.liteworkflow.engine.impl;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cache.Cache;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.parser.ModelParser;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.request.ProcessDefPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @author yuqs
 * @since 1.0
 */
public class RepositoryServiceImpl extends AccessService implements RepositoryService
{
	private static final Logger log = LoggerFactory.getLogger(RepositoryServiceImpl.class);

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
	private Cache<String, ProcessDefinition> entityCache;

	/**
	 * 名称cache(key=id,value=name对象)
	 */
	private Cache<String, String> nameCache;

	private ProcessEngineConfiguration engineConfiguration;

	private ProcessDefinitionEntityService processDefinitionEntityService;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	@Override
	public void check(ProcessDefinition process, String idOrName)
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
	@Override
	public void saveProcess(ProcessDefinition process)
	{
		processDefinitionEntityService.save(process);
	}

	/**
	 * 更新process的类别
	 */
	@Override
	public void updateType(String id, String type)
	{
		ProcessDefinition entity = getProcessById(id);
		entity.setType(type);
		processDefinitionEntityService.updateProcessType(id, type);
		cache(entity);
	}

	/**
	 * 根据id获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	@Override
	public ProcessDefinition getProcessById(String id)
	{
		AssertHelper.notEmpty(id);
		ProcessDefinition entity = null;
		String processName;
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, ProcessDefinition> entityCache = ensureAvailableEntityCache();
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
			log.debug("obtain process[id={}] from cache.", id);

			return entity;
		}
		entity = processDefinitionEntityService.getProcess(id);
		if (entity != null)
		{
			log.debug("obtain process[id={}] from database.", id);

			cache(entity);
		}
		return entity;
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	@Override
	public ProcessDefinition getProcessByName(String name)
	{
		return getProcessByVersion(name, null);
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	@Override
	public ProcessDefinition getProcessByVersion(String name, Integer version)
	{
		AssertHelper.notEmpty(name);
		if (version == null)
		{
			version = processDefinitionEntityService.getLatestProcessVersion(name);
		}
		if (version == null)
		{
			version = 0;
		}
		ProcessDefinition entity = null;
		String processName = name + DEFAULT_SEPARATOR + version;
		Cache<String, ProcessDefinition> entityCache = ensureAvailableEntityCache();
		if (entityCache != null)
		{
			entity = entityCache.get(processName);
		}
		if (entity != null)
		{
			log.debug("obtain process[name={}] from cache.", processName);

			return entity;
		}

		ProcessDefPageRequest request = new ProcessDefPageRequest();
		request.setNames(new String[] { name });
		request.setVersion(version);
		List<ProcessDefinition> processs = processDefinitionEntityService.queryList(request);
		if (processs != null && !processs.isEmpty())
		{
			log.debug("obtain process[name={}] from database.", processName);

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
	@Override
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
	@Override
	public String deploy(InputStream input, String creator)
	{
		try
		{
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = ModelParser.parse(bytes);
			Integer version = processDefinitionEntityService.getLatestProcessVersion(model.getName());
			ProcessDefinition entity = new ProcessDefinition();
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
			throw new ProcessException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据流程定义id、xml的输入流解析为字节数组，保存至数据库中，并且重新put到缓存中
	 * 
	 * @param input 定义输入流
	 */
	@Override
	public void redeploy(String id, InputStream input)
	{
		ProcessDefinition entity = processDefinitionEntityService.getProcess(id);

		try
		{
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = ModelParser.parse(bytes);
			String oldProcessName = entity.getName();
			entity.setModel(model);
			entity.setBytes(bytes);
			processDefinitionEntityService.update(entity);
			if (!oldProcessName.equalsIgnoreCase(entity.getName()))
			{
				Cache<String, ProcessDefinition> entityCache = ensureAvailableEntityCache();
				if (entityCache != null)
				{
					entityCache.remove(oldProcessName + DEFAULT_SEPARATOR + entity.getVersion());
				}
			}
			cache(entity);
		}
		catch (Exception e)
		{
			throw new ProcessException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据processId卸载流程
	 */
	@Override
	public void undeploy(String id)
	{
		ProcessDefinition entity = processDefinitionEntityService.getProcess(id);
		entity.setState(Constants.STATE_FINISH);
		processDefinitionEntityService.update(entity);
		cache(entity);
	}

	/**
	 * 级联删除指定流程定义的所有数据
	 */
	@Override
	public void cascadeRemove(String id)
	{
		ProcessDefinition entity = processDefinitionEntityService.getProcess(id);

		HistoricProcessInstPageRequest request = new HistoricProcessInstPageRequest();
		request.setProcessId(id);
		List<HistoricProcessInstance> historyOrders = historicProcessInstanceEntityService.queryList(request);

		for (HistoricProcessInstance historyOrder : historyOrders)
		{
			engineConfiguration.getProcessInstanceService().cascadeRemove(historyOrder.getId());
		}
		processDefinitionEntityService.delete(entity);
		clear(entity);
	}

	/**
	 * 查询流程定义
	 */
	@Override
	public List<ProcessDefinition> getProcesss(ProcessDefPageRequest request)
	{
		return processDefinitionEntityService.queryList(request);
	}

	/**
	 * 分页查询流程定义
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessDefPageRequest request)
	{
		return processDefinitionEntityService.queryPageData(request);
	}

	/**
	 * 缓存实体
	 * 
	 * @param entity 流程定义对象
	 */
	private void cache(ProcessDefinition entity)
	{
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, ProcessDefinition> entityCache = ensureAvailableEntityCache();
		if (entity.getModel() == null && entity.getDBContent() != null)
		{
			entity.setModel(ModelParser.parse(entity.getDBContent()));
		}
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if (nameCache != null && entityCache != null)
		{
			log.debug("cache process id is[{}],name is[{}]", entity.getId(), processName);

			entityCache.put(processName, entity);
			nameCache.put(entity.getId(), processName);
		}
		else
		{
			log.debug("no cache implementation class");
		}
	}

	/**
	 * 清除实体
	 * 
	 * @param entity 流程定义对象
	 */
	private void clear(ProcessDefinition entity)
	{
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, ProcessDefinition> entityCache = ensureAvailableEntityCache();
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if (nameCache != null && entityCache != null)
		{
			nameCache.remove(entity.getId());
			entityCache.remove(processName);
		}
	}

	private Cache<String, ProcessDefinition> ensureAvailableEntityCache()
	{
		Cache<String, ProcessDefinition> entityCache = ensureEntityCache();
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

	public Cache<String, ProcessDefinition> ensureEntityCache()
	{
		return entityCache;
	}

	public void setEntityCache(Cache<String, ProcessDefinition> entityCache)
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
	 * 设置engineConfiguration
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}

	/**
	 * 设置processDefinitionEntityService
	 * 
	 * @param processDefinitionEntityService
	 */
	public void setProcessDefinitionEntityService(ProcessDefinitionEntityService processDefinitionEntityService)
	{
		this.processDefinitionEntityService = processDefinitionEntityService;
	}

	/**
	 * 设置historicProcessInstanceEntityService
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}
}
