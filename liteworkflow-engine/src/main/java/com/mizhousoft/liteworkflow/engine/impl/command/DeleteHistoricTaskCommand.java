package com.mizhousoft.liteworkflow.engine.impl.command;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;

/**
 * 删除历史任务指令
 *
 * @version
 */
public class DeleteHistoricTaskCommand implements Command<Void>
{
	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 */
	public DeleteHistoricTaskCommand(int taskId)
	{
		super();
		this.taskId = taskId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();
		HistoricVariableEntityService historicVariableEntityService = engineConfiguration.getHistoricVariableEntityService();

		HistoricTaskEntity historicTask = historicTaskEntityService.getById(taskId);
		if (null != historicTask)
		{
			historicTaskEntityService.deleteEntity(historicTask);

			historicVariableEntityService.deleteByTaskId(historicTask.getInstanceId(), taskId);
		}

		return null;
	}
}
