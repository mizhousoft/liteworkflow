package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.CompleteTaskCommand;
import com.liteworkflow.engine.impl.command.SetTaskVariablesCommand;
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
	/**
	 * 任务实体服务
	 */
	private TaskEntityService taskEntityService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param taskEntityService
	 */
	public TaskServiceImpl(ProcessEngineConfigurationImpl engineConfiguration, TaskEntityService taskEntityService)
	{
		super(engineConfiguration);

		this.taskEntityService = taskEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> complete(String taskId)
	{
		return complete(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> complete(String taskId, String operator)
	{
		return complete(taskId, operator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> complete(String taskId, String operator, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new CompleteTaskCommand(taskId, operator, variableMap));

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariable(String taskId, String variableName, Object value)
	{
		Map<String, Object> variableMap = new HashMap<>(1);
		variableMap.put(variableName, value);

		setVariables(taskId, variableMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariables(String taskId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new SetTaskVariablesCommand(taskId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(String taskId)
	{
		return taskEntityService.getById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryByInstanceId(String instanceId)
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
}
