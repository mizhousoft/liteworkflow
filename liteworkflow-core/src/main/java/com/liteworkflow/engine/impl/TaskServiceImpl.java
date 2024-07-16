package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.CompleteTaskCommand;
import com.liteworkflow.engine.impl.command.SetTaskVariablesCommand;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.query.TaskQuery;

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
	public List<Task> complete(int taskId)
	{
		return complete(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> complete(int taskId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new CompleteTaskCommand(taskId, variableMap));

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariable(int taskId, String variableName, Object value)
	{
		Map<String, Object> variableMap = new HashMap<>(1);
		variableMap.put(variableName, value);

		setVariables(taskId, variableMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariables(int taskId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new SetTaskVariablesCommand(taskId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskQuery createTaskQuery()
	{
		return new TaskQueryImpl(taskEntityService);
	}
}
