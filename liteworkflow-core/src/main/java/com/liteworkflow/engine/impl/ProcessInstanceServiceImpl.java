package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.DeleteProcessInstanceCommand;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;

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
	public void deleteInstance(String instanceId)
	{
		commandExecutor.execute(new DeleteProcessInstanceCommand(instanceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance getInstance(String instanceId)
	{
		return processInstanceEntityService.getById(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request)
	{
		return processInstanceEntityService.queryPageData(request);
	}
}
