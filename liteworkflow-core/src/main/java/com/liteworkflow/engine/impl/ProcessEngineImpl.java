package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.TaskService;

/**
 * 基本的流程引擎实现类
 * 
 * @author yuqs
 * @since 1.0
 */
public class ProcessEngineImpl implements ProcessEngine
{
	/**
	 * Snaker配置对象
	 */
	protected ProcessEngineConfiguration configuration;

	/**
	 * 流程定义业务类
	 */
	protected RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	protected ProcessInstanceService processInstanceService;

	/**
	 * 任务业务类
	 */
	protected TaskService taskService;

	/**
	 * 查询业务类
	 */
	protected HistoryService historyService;

	/**
	 * 管理业务类
	 */
	protected ManagerService managerService;

	protected RuntimeService runtimeService;

	/**
	 * 构造函数
	 *
	 * @param configuration
	 */
	public ProcessEngineImpl(ProcessEngineConfiguration configuration)
	{
		super();
		this.configuration = configuration;

		this.managerService = configuration.getManagerService();
		this.processInstanceService = configuration.getProcessInstanceService();
		this.repositoryService = configuration.getRepositoryService();
		this.historyService = configuration.getHistoryService();
		this.taskService = configuration.getTaskService();
		this.runtimeService = configuration.getRuntimeService();
	}

	/**
	 * 获取流程定义服务
	 */
	@Override
	public RepositoryService getRepositoryService()
	{
		return repositoryService;
	}

	@Override
	public HistoryService getHistoryService()
	{
		return historyService;
	}

	/**
	 * 获取实例服务
	 * 
	 * @since 1.2.2
	 */
	@Override
	public ProcessInstanceService getProcessInstanceService()
	{
		return processInstanceService;
	}

	/**
	 * 获取任务服务
	 * 
	 * @since 1.2.2
	 */
	@Override
	public TaskService getTaskService()
	{
		return taskService;
	}

	/**
	 * 获取管理服务
	 * 
	 * @since 1.4
	 */
	@Override
	public ManagerService getManagerService()
	{
		return managerService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RuntimeService getRuntimeService()
	{
		return runtimeService;
	}
}
