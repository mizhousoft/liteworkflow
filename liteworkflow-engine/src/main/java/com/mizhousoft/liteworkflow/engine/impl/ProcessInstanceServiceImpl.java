package com.mizhousoft.liteworkflow.engine.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.liteworkflow.engine.ProcessInstanceService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;

/**
 * 流程实例业务类
 * 
 * @version
 */
public class ProcessInstanceServiceImpl extends CommonServiceImpl implements ProcessInstanceService
{
	/**
	 * ProcessInstanceEntityService
	 */
	private ProcessInstanceEntityService processInstanceEntityService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param processInstanceEntityService
	 */
	public ProcessInstanceServiceImpl(ProcessEngineConfigurationImpl engineConfiguration,
	        ProcessInstanceEntityService processInstanceEntityService)
	{
		super(engineConfiguration);

		this.processInstanceEntityService = processInstanceEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance getInstance(int instanceId)
	{
		return processInstanceEntityService.getById(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance loadInstance(int instanceId)
	{
		ProcessInstanceEntity instance = processInstanceEntityService.getById(instanceId);

		Assert.notNull(instance, "ProcessInstance not found, id is " + instanceId);

		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessInstance> queryInstanceList(Set<Integer> instanceIds)
	{
		if (CollectionUtils.isEmpty(instanceIds))
		{
			return Collections.emptyList();
		}

		List<ProcessInstanceEntity> list = processInstanceEntityService.queryByIds(instanceIds);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, ProcessInstance> queryInstanceMap(Set<Integer> instanceIds)
	{
		List<ProcessInstance> list = queryInstanceList(instanceIds);

		return list.stream().collect(Collectors.toMap(ProcessInstance::getId, o -> o));
	}
}
