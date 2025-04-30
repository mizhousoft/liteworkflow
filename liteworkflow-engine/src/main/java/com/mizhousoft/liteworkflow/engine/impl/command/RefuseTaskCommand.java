package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.TaskStatusEnum;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 拒绝任务命令
 *
 */
public class RefuseTaskCommand implements Command<Void>
{
	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 变量
	 */
	private Map<String, Object> taskVariableMap;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param variableMap
	 */
	public RefuseTaskCommand(int taskId, Map<String, Object> variableMap)
	{
		super();
		this.taskId = taskId;
		this.taskVariableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		TaskEntity task = taskEntityService.loadById(taskId);
		task.setStatus(TaskStatusEnum.COMPLETED.getValue());

		TaskUtils.complete(task, TaskStatusEnum.COMPLETED, taskVariableMap, engineConfiguration);

		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		BpmnModel bpmnModel = processDefinition.getBpmnModel();

		UserTaskModel taskModel = bpmnModel.getFlowUserTaskModel(task.getTaskDefinitionKey());

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeTaskListeners(taskModel, task, TaskListener.EVENTNAME_COMPLETE);

		List<TaskEntity> tasks = taskEntityService.queryByInstanceId(task.getInstanceId());
		for (TaskEntity t : tasks)
		{
			TaskUtils.complete(t, TaskStatusEnum.CANCELED, Collections.emptyMap(), engineConfiguration);
		}

		ProcessInstanceEntity processInstance = processInstanceEntityService.loadById(task.getInstanceId());

		Execution execution = new Execution(engineConfiguration, processDefinition, processInstance, Collections.emptyMap());

		ActivityBehavior flowExecutor = engineConfiguration.getActivityBehaviorFactory().build(bpmnModel.getEndModel());
		flowExecutor.execute(execution);

		return null;
	}

}
