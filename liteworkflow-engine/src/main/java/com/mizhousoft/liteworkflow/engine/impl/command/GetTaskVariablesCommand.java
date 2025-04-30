package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 获取任务变量指令
 *
 * @version
 */
public class GetTaskVariablesCommand implements Command<Map<String, Object>>
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
	public GetTaskVariablesCommand(int taskId)
	{
		super();
		this.taskId = taskId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		TaskEntity task = taskEntityService.loadById(taskId);

		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		return variableEntityService.queryMapByTaskId(task.getInstanceId(), taskId);
	}
}
