package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.TaskService;

/**
 * 基本的流程引擎实现类
 * 
 * @author
 * @since 1.0
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
	 * @param configuration
	 */
	public ProcessEngineImpl(ProcessEngineConfiguration configuration)
	{
		super();

		this.repositoryService = configuration.getRepositoryService();
		this.processInstanceService = configuration.getProcessInstanceService();
		this.runtimeService = configuration.getRuntimeService();
		this.taskService = configuration.getTaskService();
		this.historyService = configuration.getHistoryService();
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
