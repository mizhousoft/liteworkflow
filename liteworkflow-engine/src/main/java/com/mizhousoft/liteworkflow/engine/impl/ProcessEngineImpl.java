package com.mizhousoft.liteworkflow.engine.impl;

import com.mizhousoft.liteworkflow.engine.HistoryService;
import com.mizhousoft.liteworkflow.engine.ProcessEngine;
import com.mizhousoft.liteworkflow.engine.ProcessEngineConfiguration;
import com.mizhousoft.liteworkflow.engine.ProcessInstanceService;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.TaskService;

/**
 * 基本的流程引擎实现类
 * 
 * @version
 */
public class ProcessEngineImpl implements ProcessEngine
{
	/**
	 * 流程定义业务类
	 */
	private RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	private ProcessInstanceService processInstanceService;

	/**
	 * 任务业务类
	 */
	private TaskService taskService;

	/**
	 * 查询业务类
	 */
	private HistoryService historyService;

	/**
	 * 运行服务类
	 */
	private RuntimeService runtimeService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public ProcessEngineImpl(ProcessEngineConfiguration engineConfiguration)
	{
		super();

		this.repositoryService = engineConfiguration.getRepositoryService();
		this.processInstanceService = engineConfiguration.getProcessInstanceService();
		this.runtimeService = engineConfiguration.getRuntimeService();
		this.taskService = engineConfiguration.getTaskService();
		this.historyService = engineConfiguration.getHistoryService();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepositoryService getRepositoryService()
	{
		return repositoryService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceService getProcessInstanceService()
	{
		return processInstanceService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RuntimeService getRuntimeService()
	{
		return runtimeService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskService getTaskService()
	{
		return taskService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryService getHistoryService()
	{
		return historyService;
	}

}
