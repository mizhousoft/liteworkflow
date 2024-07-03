package com.liteworkflow.engine.impl;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cache.Cache;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.DeleteDeploymentCommand;
import com.liteworkflow.engine.impl.command.DeployCommand;
import com.liteworkflow.engine.impl.command.RedeployCommand;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.parser.ModelParser;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.ProcessDefinitionPageRequest;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @version
 */
public class RepositoryServiceImpl extends CommonServiceImpl implements RepositoryService
{
	private static final Logger LOG = LoggerFactory.getLogger(RepositoryServiceImpl.class);

	/**
	 * cache manager
	 */
	private CacheManager cacheManager;

	/**
	 * ProcessDefinitionEntityService
	 */
	private ProcessDefinitionEntityService processDefinitionEntityService;

	/**
	 * 实体cache(key=name + - + version, value=entity对象)
	 */
	private Cache<String, ProcessDefinition> entityCache;

	/**
	 * 名称cache(key=id, value=name对象)
	 */
	private Cache<String, String> nameCache;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param cacheManager
	 * @param processDefinitionEntityService
	 */
	public RepositoryServiceImpl(ProcessEngineConfigurationImpl engineConfiguration, CacheManager cacheManager,
	        ProcessDefinitionEntityService processDefinitionEntityService)
	{
		super(engineConfiguration);

		this.cacheManager = cacheManager;
		this.processDefinitionEntityService = processDefinitionEntityService;

		this.nameCache = this.cacheManager.getCache("process-name");
		this.entityCache = this.cacheManager.getCache("process-entity");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(InputStream input)
	{
		return deploy(input, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(InputStream input, String creator)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new DeployCommand(input, creator));

		putCache(processDefinition);

		return processDefinition.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void redeploy(String id, InputStream input)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new RedeployCommand(input, id));

		putCache(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDeployment(String id, boolean cascade)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new DeleteDeploymentCommand(id, cascade));

		removeCache(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getById(String id)
	{
		Assert.notNull(id, "Process definition id is null.");

		String processName = nameCache.get(id);
		if (null != processName)
		{
			ProcessDefinition processDefinition = entityCache.get(processName);
			if (processDefinition != null)
			{
				return processDefinition;
			}
		}

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(id);
		if (processDefinition != null)
		{
			putCache(processDefinition);
		}

		return processDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getLatestByName(String name)
	{
		return getByVersion(name, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getByVersion(String name, Integer version)
	{
		Assert.notNull(name, "Process definition name is null.");

		if (null == version)
		{
			version = processDefinitionEntityService.getLatestVersion(name);
		}
		Assert.notNull(version, "Process definition version is null.");

		String cacheKey = buildEntityCacheKey(name, version);
		ProcessDefinition processDefinition = entityCache.get(cacheKey);
		if (processDefinition != null)
		{
			return processDefinition;
		}

		List<ProcessDefinition> list = processDefinitionEntityService.queryByName(name, version);
		if (!list.isEmpty())
		{
			processDefinition = list.get(0);

			putCache(processDefinition);
		}

		return processDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefinition> queryList(String name)
	{
		return processDefinitionEntityService.queryByName(name, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessDefinitionPageRequest request)
	{
		return processDefinitionEntityService.queryPageData(request);
	}

	/**
	 * 加入缓存
	 * 
	 * @param processDefinition
	 */
	private void putCache(ProcessDefinition processDefinition)
	{
		if (processDefinition.getModel() == null)
		{
			ProcessModel processModel = ModelParser.parse(processDefinition.getBytes());
			processDefinition.setModel(processModel);
		}

		String cacheKey = buildEntityCacheKey(processDefinition.getName(), processDefinition.getVersion());
		entityCache.put(cacheKey, processDefinition);

		nameCache.put(processDefinition.getId(), cacheKey);

		LOG.info("Put process definition cache, id is {}, name is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * 清除缓存
	 * 
	 * @param processDefinition
	 */
	private void removeCache(ProcessDefinition processDefinition)
	{
		String cacheKey = buildEntityCacheKey(processDefinition.getName(), processDefinition.getVersion());
		entityCache.remove(cacheKey);

		nameCache.remove(processDefinition.getId());

		LOG.info("Remove process definition cache, id is {}, name is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * 构建缓存KEY
	 * 
	 * @param processName
	 * @param version
	 * @return
	 */
	private String buildEntityCacheKey(String processName, int version)
	{
		return processName + "-" + version;
	}
}
