package com.mizhousoft.liteworkflow.engine.impl.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.TaskStatusEnum;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.HistoricTaskUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 转交任务命令
 *
 */
public class TransferTaskCommand implements Command<Void>
{
	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param userId
	 */
	public TransferTaskCommand(int taskId, String userId, Map<String, Object> variableMap)
	{
		super();
		this.taskId = taskId;
		this.userId = userId;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		TaskEntity originalTask = taskEntityService.loadById(taskId);
		Map<String, Object> originalTaskVarMap = variableEntityService.queryMapByTaskId(originalTask.getInstanceId(), originalTask.getId());

		TaskUtils.complete(originalTask, TaskStatusEnum.COMPLETED, variableMap, engineConfiguration);

		TaskEntity newTask = new TaskEntity();
		BeanUtils.copyProperties(originalTask, newTask);

		newTask.setId(0);
		newTask.setOwner(originalTask.getAssignee());
		newTask.setAssignee(userId);
		newTask.setCreateTime(LocalDateTime.now());
		taskEntityService.addEntity(newTask);

		HistoricTaskUtils.createHistoricTask(newTask, engineConfiguration);

		variableEntityService.addVariables(newTask.getInstanceId(), newTask.getId(), originalTaskVarMap);

		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(newTask.getProcessDefinitionId());
		BpmnModel bpmnModel = processDefinition.getBpmnModel();
		UserTaskModel taskModel = bpmnModel.getFlowUserTaskModel(originalTask.getTaskDefinitionKey());

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeTaskListeners(taskModel, newTask, TaskListener.EVENTNAME_ASSIGNMENT);

		return null;
	}
}
