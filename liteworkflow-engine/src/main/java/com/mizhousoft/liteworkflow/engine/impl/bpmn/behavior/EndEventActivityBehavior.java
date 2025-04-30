package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.InstanceStatusEnum;
import com.mizhousoft.liteworkflow.engine.delegate.ExecutionListener;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 结束事件流程执行器
 *
 * @version
 */
public class EndEventActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 模型
	 */
	protected EndEventModel endModel;

	/**
	 * 实例状态
	 */
	protected InstanceStatusEnum instanceStatus;

	/**
	 * 构造函数
	 *
	 * @param endModel
	 * @param instanceStatus
	 */
	public EndEventActivityBehavior(EndEventModel endModel, InstanceStatusEnum instanceStatus)
	{
		super();
		this.endModel = endModel;
		this.instanceStatus = instanceStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		ProcessInstanceEntity processInstance = execution.getProcessInstance();

		List<TaskEntity> tasks = taskEntityService.queryByInstanceId(processInstance.getId());
		if (!tasks.isEmpty())
		{
			throw new WorkFlowException("There are still process tasks to complete.");
		}

		BpmnModel bpmnModel = execution.getBpmnModel();
		completeInstance(processInstance, instanceStatus, bpmnModel, execution.getEngineConfiguration());

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeExecutionListeners(bpmnModel, processInstance, ExecutionListener.EVENTNAME_END);

		return true;
	}

	/**
	 * 完成实例
	 * 
	 * @param processInstance
	 * @param instanceStatus
	 * @param bpmnModel
	 * @param engineConfiguration
	 */
	private void completeInstance(ProcessInstanceEntity processInstance, InstanceStatusEnum instanceStatus, BpmnModel bpmnModel,
	        ProcessEngineConfigurationImpl engineConfiguration)
	{
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		variableEntityService.deleteByInstanceId(processInstance.getId(), true);
		processInstanceEntityService.deleteEntity(processInstance);

		HistoricInstanceEntity historicInstance = historicProcessInstanceEntityService.loadById(processInstance.getId());
		historicInstance.setEndTime(LocalDateTime.now());
		historicInstance.setStatus(instanceStatus.getValue());

		long duration = ChronoUnit.SECONDS.between(historicInstance.getStartTime(), historicInstance.getEndTime());
		historicInstance.setDuration(duration);
		historicInstance.setRevision(historicInstance.getRevision() + 1);

		historicProcessInstanceEntityService.modifyEntity(historicInstance);
	}
}
