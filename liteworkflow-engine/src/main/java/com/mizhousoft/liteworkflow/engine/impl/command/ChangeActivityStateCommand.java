package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.TaskStatusEnum;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.HistoricTaskUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 改变活动状态命令
 *
 */
public class ChangeActivityStateCommand implements Command<Void>
{
	/**
	 * 任务ID
	 */
	private List<Integer> taskIds;

	/**
	 * 活动ID
	 */
	private String activityId;

	/**
	 * 变量
	 */
	private Map<Integer, Map<String, Object>> variableMap;

	/**
	 * 构造函数
	 *
	 * @param taskIds
	 * @param activityId
	 * @param variableMap
	 */
	public ChangeActivityStateCommand(Set<Integer> taskIds, String activityId, Map<Integer, Map<String, Object>> variableMap)
	{
		super();
		this.taskIds = null == taskIds ? new ArrayList<>(0) : new ArrayList<>(taskIds);
		this.activityId = activityId;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		List<TaskEntity> tasks = queryTaskList(engineConfiguration);
		TaskEntity oneTask = tasks.get(0);

		HistoricTaskEntity historicTask = getHistoricTask(oneTask.getInstanceId(), engineConfiguration);

		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(oneTask.getProcessDefinitionId());
		BpmnModel bpmnModel = processDefinition.getBpmnModel();

		completeCurrentTaskList(tasks, bpmnModel, engineConfiguration);

		createNewTask(historicTask, bpmnModel, engineConfiguration);

		return null;
	}

	private List<TaskEntity> queryTaskList(ProcessEngineConfigurationImpl engineConfiguration)
	{
		if (taskIds.isEmpty())
		{
			throw new WorkFlowException("Task ids is empty.");
		}

		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		TaskEntity task = taskEntityService.loadById(taskIds.get(0));

		if (taskIds.size() > 1)
		{
			List<TaskEntity> list = taskEntityService.queryByInstanceId(task.getInstanceId());

			List<TaskEntity> tasks = list.stream().filter(o -> taskIds.contains(o.getId())).toList();
			if (tasks.size() != taskIds.size())
			{
				throw new WorkFlowException("Task ids is illegal.");
			}

			return tasks;
		}
		else
		{
			return List.of(task);
		}
	}

	private HistoricTaskEntity getHistoricTask(int instanceId, ProcessEngineConfigurationImpl engineConfiguration)
	{
		HistoricTaskEntityService historicTaskService = engineConfiguration.getHistoricTaskEntityService();

		List<HistoricTaskEntity> historicTasks = historicTaskService.queryByInstanceId(instanceId);
		HistoricTaskEntity historicTask = historicTasks.stream()
		        .filter(t -> t.getTaskDefinitionKey().equals(activityId))
		        .findFirst()
		        .orElse(null);
		if (null == historicTask)
		{
			throw new WorkFlowException("Activity id is illegal, activityId is " + activityId);
		}

		return historicTask;
	}

	private void completeCurrentTaskList(List<TaskEntity> tasks, BpmnModel bpmnModel, ProcessEngineConfigurationImpl engineConfiguration)
	{
		for (TaskEntity task : tasks)
		{
			task.setStatus(TaskStatusEnum.COMPLETED.getValue());

			Map<String, Object> taskVariableMap = variableMap.computeIfAbsent(task.getId(), o -> Collections.emptyMap());

			TaskUtils.complete(task, TaskStatusEnum.COMPLETED, taskVariableMap, engineConfiguration);

			UserTaskModel taskModel = bpmnModel.getFlowUserTaskModel(task.getTaskDefinitionKey());

			ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
			listenerInvocation.executeTaskListeners(taskModel, task, TaskListener.EVENTNAME_COMPLETE);
		}
	}

	private void createNewTask(HistoricTaskEntity historicTask, BpmnModel bpmnModel, ProcessEngineConfigurationImpl engineConfiguration)
	{
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		UserTaskModel taskModel = bpmnModel.getFlowUserTaskModel(historicTask.getTaskDefinitionKey());

		TaskEntity task = TaskUtils.buildTask(taskModel, historicTask.getProcessDefinitionId(), historicTask.getInstanceId());
		task.setAssignee(historicTask.getAssignee());
		taskEntityService.addEntity(task);

		HistoricTaskUtils.createHistoricTask(task, engineConfiguration);

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeTaskListeners(taskModel, task, TaskListener.EVENTNAME_CREATE);
	}
}
