package com.liteworkflow.engine.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cache.Cache;
import com.liteworkflow.engine.cache.CacheManager;
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
 * @author
 * @since 1.0
 */
public class RepositoryServiceImpl extends AccessService implements RepositoryService
{
	private static final Logger log = LoggerFactory.getLogger(RepositoryServiceImpl.class);

	/**
	 * ProcessEngineConfiguration
	 */
	private ProcessEngineConfiguration engineConfiguration;

	/**
	 * cache manager
	 */
	private CacheManager cacheManager;

	/**
	 * ProcessDefinitionEntityService
	 */
	private ProcessDefinitionEntityService processDefinitionEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

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
	 * @param historicProcessInstanceEntityService
	 */
	public RepositoryServiceImpl(ProcessEngineConfiguration engineConfiguration, CacheManager cacheManager,
	        ProcessDefinitionEntityService processDefinitionEntityService,
	        HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		super();
		this.engineConfiguration = engineConfiguration;
		this.cacheManager = cacheManager;
		this.processDefinitionEntityService = processDefinitionEntityService;
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;

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
		try
		{
			byte[] bytes = IOUtils.toByteArray(input);
			ProcessModel processModel = ModelParser.parse(bytes);

			Integer version = processDefinitionEntityService.getLatestVersion(processModel.getName());
			if (version == null)
			{
				version = 0;
			}
			else
			{
				version = version + 1;
			}

			ProcessDefinition processDefinition = new ProcessDefinition();
			processDefinition.setId(StringHelper.getPrimaryKey());
			processDefinition.setName(processModel.getName());
			processDefinition.setDisplayName(processModel.getDisplayName());
			processDefinition.setCategory(processModel.getCategory());
			processDefinition.setState(Constants.STATE_ACTIVE);
			processDefinition.setVersion(version);
			processDefinition.setBytes(bytes);
			processDefinition.setInstanceUrl(processModel.getInstanceUrl());
			processDefinition.setCreateTime(LocalDateTime.now());
			processDefinition.setCreator(creator);
			processDefinition.setModel(processModel);

			processDefinitionEntityService.addEntity(processDefinition);

			putCache(processDefinition);

			return processDefinition.getId();
		}
		catch (IOException e)
		{
			throw new ProcessException("Parse process model failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void redeploy(String id, InputStream input)
	{
		ProcessDefinition processDefinition = processDefinitionEntityService.getById(id);
		Assert.notNull(processDefinition, "Process definition not found, id is " + id);

		try
		{
			String oldProcessName = processDefinition.getName();

			byte[] bytes = IOUtils.toByteArray(input);
			ProcessModel processModel = ModelParser.parse(bytes);

			processDefinition.setName(processModel.getName());
			processDefinition.setDisplayName(processModel.getDisplayName());
			processDefinition.setCategory(processModel.getCategory());
			processDefinition.setBytes(bytes);
			processDefinition.setInstanceUrl(processModel.getInstanceUrl());
			processDefinition.setModel(processModel);

			processDefinitionEntityService.modifyEntity(processDefinition);

			if (!oldProcessName.equalsIgnoreCase(processDefinition.getName()))
			{
				String key = buildEntityCacheKey(oldProcessName, processDefinition.getVersion());
				entityCache.remove(key);
			}

			putCache(processDefinition);
		}
		catch (IOException e)
		{
			throw new ProcessException("Parse process model failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void undeploy(String id)
	{
		ProcessDefinition entity = processDefinitionEntityService.getById(id);
		entity.setState(Constants.STATE_FINISH);
		processDefinitionEntityService.modifyEntity(entity);
		putCache(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cascadeRemove(String id)
	{
		ProcessDefinition processDefinition = processDefinitionEntityService.getById(id);

		HistoricProcessInstPageRequest request = new HistoricProcessInstPageRequest();
		request.setProcessId(id);
		List<HistoricProcessInstance> historicInstances = historicProcessInstanceEntityService.queryList(request);

		ProcessInstanceService processInstanceService = engineConfiguration.getProcessInstanceService();

		for (HistoricProcessInstance historicInstance : historicInstances)
		{
			processInstanceService.cascadeRemove(historicInstance.getId());
		}

		processDefinitionEntityService.deleteEntity(processDefinition);

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
	public List<ProcessDefinition> queryListByName(String name)
	{
		return processDefinitionEntityService.queryByName(name, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessDefPageRequest request)
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

		log.info("Put process definition cache, id is {}, name is {}.", processDefinition.getId(), cacheKey);
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

		log.info("Remove process definition cache, id is {}, name is {}.", processDefinition.getId(), cacheKey);
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
