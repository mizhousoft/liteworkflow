package com.liteworkflow.engine.impl;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.CompleteTaskCommand;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务执行业务类
 * 
 * @version
 */
public class TaskServiceImpl extends CommonServiceImpl implements TaskService
{
	private TaskEntityService taskEntityService;

	/**
	 * 流程定义业务类
	 */
	protected RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	protected ProcessInstanceService processInstanceService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public TaskServiceImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super(engineConfiguration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId)
	{
		return executeTask(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator)
	{
		return executeTask(taskId, operator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args)
	{
		commandExecutor.execute(new CompleteTaskCommand(taskId, operator, args));

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(String taskId)
	{
		return taskEntityService.getTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> getActiveTasks(String instanceId)
	{
		return taskEntityService.queryByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		return taskEntityService.queryPageData(request);
	}

	/**
	 * 设置taskEntityService
	 * 
	 * @param taskEntityService
	 */
	public void setTaskEntityService(TaskEntityService taskEntityService)
	{
		this.taskEntityService = taskEntityService;
	}

	/**
	 * 设置repositoryService
	 * 
	 * @param repositoryService
	 */
	public void setRepositoryService(RepositoryService repositoryService)
	{
		this.repositoryService = repositoryService;
	}

	/**
	 * 设置processInstanceService
	 * 
	 * @param processInstanceService
	 */
	public void setProcessInstanceService(ProcessInstanceService processInstanceService)
	{
		this.processInstanceService = processInstanceService;
	}
}
