package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Collections;
import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.InstanceStatusEnum;
import com.mizhousoft.liteworkflow.engine.constant.TaskStatusEnum;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior.EndEventActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 撤销流程命令
 *
 */
public class TerminateProcessInstanceCommand implements Command<Void>
{
	/**
	 * 流程实例ID
	 */
	private int instanceId;

	/**
	 * 构造函数
	 *
	 * @param instanceId
	 */
	public TerminateProcessInstanceCommand(int instanceId)
	{
		super();
		this.instanceId = instanceId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstanceEntity processInstance = processInstanceEntityService.loadById(instanceId);

		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		List<TaskEntity> tasks = taskEntityService.queryByInstanceId(instanceId);
		for (TaskEntity task : tasks)
		{
			TaskUtils.complete(task, TaskStatusEnum.TERMINATED, Collections.emptyMap(), engineConfiguration);
		}

		RepositoryService repositoryService = engineConfiguration.getRepositoryService();
		ProcessDefEntity processDefinition = (ProcessDefEntity) repositoryService
		        .getProcessDefinition(processInstance.getProcessDefinitionId());
		BpmnModel bpmnModel = processDefinition.getBpmnModel();

		Execution execution = new Execution(engineConfiguration, processDefinition, processInstance, Collections.emptyMap());

		ActivityBehavior flowExecutor = new EndEventActivityBehavior(bpmnModel.getEndModel(), InstanceStatusEnum.TERMINATED);
		flowExecutor.execute(execution);

		return null;
	}

}
