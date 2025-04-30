package com.mizhousoft.liteworkflow.engine.impl.command;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;

/**
 * 删除历史实例指令
 *
 * @version
 */
public class DeleteHistoricInstanceCommand implements Command<Void>
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
	public DeleteHistoricInstanceCommand(int instanceId)
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
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();
		HistoricVariableEntityService historicVariableEntityService = engineConfiguration.getHistoricVariableEntityService();

		HistoricInstanceEntity historicInstance = historicProcessInstanceEntityService.getById(instanceId);
		if (null != historicInstance)
		{
			historicProcessInstanceEntityService.deleteEntity(historicInstance);

			historicTaskEntityService.deleteByInstanceId(instanceId);

			historicVariableEntityService.deleteByInstanceId(instanceId);
		}

		return null;
	}
}
