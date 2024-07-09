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
import com.liteworkflow.engine.impl.command.BpmnParseCommand;
import com.liteworkflow.engine.impl.command.DeleteDeploymentCommand;
import com.liteworkflow.engine.impl.command.DeployCommand;
import com.liteworkflow.engine.impl.command.RedeployCommand;
import com.liteworkflow.engine.model.BpmnModel;
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
	 * Cache Manager
	 */
	private CacheManager cacheManager;

	/**
	 * ProcessDefinitionEntityService
	 */
	private ProcessDefinitionEntityService processDefinitionEntityService;

	/**
	 * 实体缓存Cache<processDefinitionKey + - + version, entity>
	 */
	private Cache<String, ProcessDefinition> entityCache;

	/**
	 * Key缓存Cache<processDefinitionId, processDefinitionKey + - + version>
	 */
	private Cache<String, String> keyVersionCache;

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

		this.keyVersionCache = this.cacheManager.getCache("process-key");
		this.entityCache = this.cacheManager.getCache("process-entity");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(InputStream istream)
	{
		return deploy(istream, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deploy(InputStream istream, String creator)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new DeployCommand(istream, creator));

		putCache(processDefinition);

		return processDefinition.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void redeploy(String processDefinitionId, InputStream istream)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new RedeployCommand(istream, processDefinitionId));

		putCache(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDeployment(String processDefinitionId, boolean cascade)
	{
		ProcessDefinition processDefinition = commandExecutor.execute(new DeleteDeploymentCommand(processDefinitionId, cascade));

		removeCache(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getProcessDefinition(String processDefinitionId)
	{
		Assert.notNull(processDefinitionId, "Process definition id is null.");

		String cacheKey = keyVersionCache.get(processDefinitionId);
		if (null != cacheKey)
		{
			ProcessDefinition processDefinition = entityCache.get(cacheKey);
			if (processDefinition != null)
			{
				return processDefinition;
			}
		}

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(processDefinitionId);
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
	public ProcessDefinition getLatestByKey(String processDefinitionKey)
	{
		return getByVersion(processDefinitionKey, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getByVersion(String processDefinitionKey, Integer version)
	{
		Assert.notNull(processDefinitionKey, "Process definition key is null.");

		if (null == version)
		{
			version = processDefinitionEntityService.getLatestVersion(processDefinitionKey);
		}
		Assert.notNull(version, "Process definition version is null.");

		String cacheKey = buildEntityCacheKey(processDefinitionKey, version);
		ProcessDefinition processDefinition = entityCache.get(cacheKey);
		if (processDefinition != null)
		{
			return processDefinition;
		}

		List<ProcessDefinition> list = processDefinitionEntityService.queryByKey(processDefinitionKey, version);
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
	public List<ProcessDefinition> queryList(String processDefinitionKey)
	{
		return processDefinitionEntityService.queryByKey(processDefinitionKey, null);
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
		if (processDefinition.getBpmnModel() == null)
		{
			byte[] bytes = processDefinition.getBytes();

			BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new BpmnParseCommand(bytes));
			processDefinition.setBpmnModel(bpmnModel);
		}

		String cacheKey = buildEntityCacheKey(processDefinition.getKey(), processDefinition.getVersion());
		entityCache.put(cacheKey, processDefinition);

		keyVersionCache.put(processDefinition.getId(), cacheKey);

		LOG.info("Put process definition cache, id is {}, cacheKey is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * 清除缓存
	 * 
	 * @param processDefinition
	 */
	private void removeCache(ProcessDefinition processDefinition)
	{
		String cacheKey = buildEntityCacheKey(processDefinition.getKey(), processDefinition.getVersion());
		entityCache.remove(cacheKey);

		keyVersionCache.remove(processDefinition.getId());

		LOG.info("Remove process definition cache, id is {}, cacheKey is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * 构建缓存KEY
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	private String buildEntityCacheKey(String processDefinitionKey, int version)
	{
		return processDefinitionKey + "-" + version;
	}
}
