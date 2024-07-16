package com.liteworkflow.engine.impl.executor;

import java.time.LocalDateTime;
import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Constants;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

/**
 * 结束事件流程执行器
 *
 * @version
 */
public class EndEventExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean doExecute(Execution execution, FlowNode nodeModel)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskService taskService = engineConfiguration.getTaskService();
		ProcessInstance processInstance = execution.getProcessInstance();

		List<Task> tasks = taskService.queryByInstanceId(processInstance.getId());
		if (!tasks.isEmpty())
		{
			throw new WorkFlowException("存在未完成的主办任务,请确认.");
		}

		complete(processInstance.getId(), execution);

		return true;
	}

	private void complete(int instanceId, Execution execution)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();

		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getByInstanceId(instanceId);
		historicInstance.setState(Constants.STATE_FINISH);
		historicInstance.setEndTime(LocalDateTime.now());
		historicProcessInstanceEntityService.modifyEntity(historicInstance);

		ProcessInstance instance = processInstanceEntityService.getById(instanceId);
		processInstanceEntityService.deleteEntity(instance);
	}
}
