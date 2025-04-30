package com.mizhousoft.liteworkflow.engine.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.liteworkflow.engine.TaskService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.Task;
import com.mizhousoft.liteworkflow.engine.impl.command.ChangeActivityStateCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.CompleteTaskCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.GetTaskVariableMapCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.GetTaskVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.RefuseTaskCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.RemoveTaskVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.SetTaskVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.TransferTaskCommand;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.request.TaskPageRequest;

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
	public void complete(int taskId)
	{
		complete(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void complete(int taskId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new CompleteTaskCommand(taskId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refuse(int taskId)
	{
		refuse(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refuse(int taskId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new RefuseTaskCommand(taskId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transfer(int taskId, String userId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new TransferTaskCommand(taskId, userId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void backToActivityId(Set<Integer> taskIds, String activityId, Map<Integer, Map<String, Object>> variableMap)
	{
		commandExecutor.execute(new ChangeActivityStateCommand(taskIds, activityId, variableMap));
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
	public Object removeVariable(int taskId, String variableName)
	{
		Map<String, Object> valueMap = removeVariables(taskId, Set.of(variableName));

		return valueMap.get(variableName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> removeVariables(int taskId, Collection<String> variableNames)
	{
		return commandExecutor.execute(new RemoveTaskVariablesCommand(taskId, variableNames));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getVariableMap(int taskId)
	{
		return commandExecutor.execute(new GetTaskVariablesCommand(taskId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> queryVariableMap(Set<Integer> instanceIds, Set<Integer> taskIds)
	{
		return commandExecutor.execute(new GetTaskVariableMapCommand(instanceIds, taskIds));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(int taskId)
	{
		return taskEntityService.getById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryTaskList(int instanceId)
	{
		List<TaskEntity> list = taskEntityService.queryByInstanceId(instanceId);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryTaskList(Set<Integer> instanceIds)
	{
		List<TaskEntity> list = taskEntityService.queryByInstanceIds(instanceIds);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, List<Task>> queryTaskMap(Set<Integer> instanceIds)
	{
		List<Task> tasks = queryTaskList(instanceIds);

		return tasks.stream().collect(Collectors.groupingBy(Task::getInstanceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		Page<TaskEntity> page = taskEntityService.queryPageData(request);

		return PageBuilder.build(new ArrayList<>(page.getContent()), request, page.getTotalNumber());
	}

}
