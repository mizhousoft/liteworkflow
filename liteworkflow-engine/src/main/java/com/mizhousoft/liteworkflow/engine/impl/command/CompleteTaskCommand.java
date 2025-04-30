package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
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
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 完成任务指令
 *
 * @version
 */
public class CompleteTaskCommand implements Command<Void>
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
	public CompleteTaskCommand(int taskId, Map<String, Object> variableMap)
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

		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		TaskEntity task = taskEntityService.loadById(taskId);
		task.setStatus(TaskStatusEnum.COMPLETED.getValue());

		TaskUtils.complete(task, TaskStatusEnum.COMPLETED, taskVariableMap, engineConfiguration);

		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		BpmnModel bpmnModel = processDefinition.getBpmnModel();
		UserTaskModel taskModel = bpmnModel.getFlowUserTaskModel(task.getTaskDefinitionKey());

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeTaskListeners(taskModel, task, TaskListener.EVENTNAME_COMPLETE);

		executeNextFlowNodeList(task, engineConfiguration);

		return null;
	}

	/**
	 * 执行后面的流程节点
	 * 
	 * @param task
	 * @param engineConfiguration
	 */
	private void executeNextFlowNodeList(TaskEntity task, ProcessEngineConfigurationImpl engineConfiguration)
	{
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		Map<String, Object> instanceVarMap = variableEntityService.queryMapByTaskId(task.getInstanceId(), 0);

		Map<String, Object> variables = new HashMap<>(instanceVarMap);
		variables.putAll(taskVariableMap);

		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		ProcessInstanceEntity instance = processInstanceEntityService.loadById(task.getInstanceId());
		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(instance.getProcessDefinitionId());

		Execution execution = new Execution(engineConfiguration, processDefinition, instance, variables);
		execution.setTask(task);

		BpmnModel bpmnModel = processDefinition.getBpmnModel();
		FlowNode flowNode = bpmnModel.getFlowNodeModel(task.getTaskDefinitionKey());
		List<SequenceFlowModel> outgoingFlows = flowNode.getOutgoingFlows();

		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			ActivityBehavior executor = engineConfiguration.getActivityBehaviorFactory().build(outgoingFlow);
			executor.execute(execution);
		}
	}
}
