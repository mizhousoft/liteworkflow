package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.List;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 删除流程实例命令
 *
 * @version
 */
public class DeleteProcessInstanceCommand implements Command<ProcessInstanceEntity>
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
	public ProcessInstanceEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		HistoricVariableEntityService historicVariableEntityService = engineConfiguration.getHistoricVariableEntityService();

		ProcessInstanceEntity instance = processInstanceEntityService.getById(instanceId);
		if (instance != null)
		{
			processInstanceEntityService.deleteEntity(instance);

			List<TaskEntity> tasks = taskEntityService.queryByInstanceId(instanceId);
			for (TaskEntity task : tasks)
			{
				taskEntityService.deleteEntity(task);
			}

			variableEntityService.deleteByInstanceId(instanceId, true);
		}

		HistoricInstanceEntity historicInstance = historicProcessInstanceEntityService.getById(instanceId);
		if (null != historicInstance)
		{
			historicProcessInstanceEntityService.deleteEntity(historicInstance);

			historicTaskEntityService.deleteByInstanceId(instanceId);

			historicVariableEntityService.deleteByInstanceId(instanceId);
		}

		return instance;
	}
}
