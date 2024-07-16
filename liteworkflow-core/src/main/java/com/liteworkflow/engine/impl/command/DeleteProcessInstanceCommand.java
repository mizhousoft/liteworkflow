package com.liteworkflow.engine.impl.command;

import java.util.List;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;

/**
 * 删除流程实例命令
 *
 * @version
 */
public class DeleteProcessInstanceCommand implements Command<ProcessInstance>
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
	public DeleteProcessInstanceCommand(int instanceId)
	{
		super();
		this.instanceId = instanceId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();

		ProcessInstance instance = processInstanceEntityService.getById(instanceId);
		if (instance != null)
		{
			processInstanceEntityService.deleteEntity(instance);
		}

		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getByInstanceId(instanceId);
		historicProcessInstanceEntityService.deleteEntity(historicInstance);

		List<Task> tasks = taskEntityService.queryByInstanceId(instanceId);
		for (Task task : tasks)
		{
			taskEntityService.deleteEntity(task);
		}

		List<HistoricTask> historicTasks = historicTaskEntityService.queryByInstanceId(instanceId);
		for (HistoricTask historicTask : historicTasks)
		{
			historicTaskEntityService.deleteEntity(historicTask);
		}

		return instance;
	}
}
