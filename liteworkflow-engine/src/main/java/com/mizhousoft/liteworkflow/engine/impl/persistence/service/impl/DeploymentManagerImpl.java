package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.cache.Cache;
import com.mizhousoft.liteworkflow.engine.cache.CacheManager;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.CommonServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.command.ParseBpmnCommand;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;

/**
 * 部署管理器
 *
 * @version
 */
public class DeploymentManagerImpl extends CommonServiceImpl implements DeploymentManager
{
	private static final Logger LOG = LoggerFactory.getLogger(DeploymentManagerImpl.class);

	/**
	 * ProcessDefEntityService
	 */
	private ProcessDefEntityService processDefEntityService;

	/**
	 * 实体缓存Cache<processDefinitionKey + - + version, entity>
	 */
	private Cache<String, ProcessDefEntity> entityCache;

	/**
	 * Key缓存Cache<processDefinitionId, processDefinitionKey + - + version>
	 */
	private Cache<Integer, String> keyVersionCache;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param processDefEntityService
	 * @param cacheManager
	 */
	public DeploymentManagerImpl(ProcessEngineConfigurationImpl engineConfiguration, ProcessDefEntityService processDefEntityService,
	        CacheManager cacheManager)
	{
		super(engineConfiguration);

		this.processDefEntityService = processDefEntityService;

		this.keyVersionCache = cacheManager.getCache("process-key");
		this.entityCache = cacheManager.getCache("process-entity");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putCache(ProcessDefEntity processDefinition)
	{
		if (processDefinition.getBpmnModel() == null)
		{
			byte[] bytes = processDefinition.getBytes();

			BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new ParseBpmnCommand(bytes));
			processDefinition.setBpmnModel(bpmnModel);
		}

		String cacheKey = buildEntityCacheKey(processDefinition.getKey(), processDefinition.getVersion());
		entityCache.put(cacheKey, processDefinition);

		keyVersionCache.put(processDefinition.getId(), cacheKey);

		LOG.info("Put process definition cache, id is {}, cacheKey is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCache(ProcessDefEntity processDefinition)
	{
		String cacheKey = buildEntityCacheKey(processDefinition.getKey(), processDefinition.getVersion());
		entityCache.remove(cacheKey);

		keyVersionCache.remove(processDefinition.getId());

		LOG.info("Remove process definition cache, id is {}, cacheKey is {}.", processDefinition.getId(), cacheKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity getProcessDefinition(int processDefinitionId)
	{
		String cacheKey = keyVersionCache.get(processDefinitionId);
		if (null != cacheKey)
		{
			ProcessDefEntity processDefinition = entityCache.get(cacheKey);
			if (processDefinition != null)
			{
				return processDefinition;
			}
		}

		ProcessDefEntity processDefinition = processDefEntityService.getById(processDefinitionId);
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
	public ProcessDefEntity getLatestByKey(String processDefinitionKey)
	{
		return getByVersion(processDefinitionKey, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity getByVersion(String processDefinitionKey, Integer version)
	{
		Assert.notNull(processDefinitionKey, "Process definition key is null.");

		if (null == version)
		{
			version = processDefEntityService.getLatestVersion(processDefinitionKey);
		}
		Assert.notNull(version, "Process definition version is null.");

		String cacheKey = buildEntityCacheKey(processDefinitionKey, version);
		ProcessDefEntity processDefinition = entityCache.get(cacheKey);
		if (processDefinition != null)
		{
			return processDefinition;
		}

		processDefinition = processDefEntityService.getByVersion(processDefinitionKey, version);
		if (null != processDefinition)
		{
			putCache(processDefinition);
		}

		return processDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefEntity> queryList(String processDefinitionKey)
	{
		return processDefEntityService.queryByKey(processDefinitionKey);
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
