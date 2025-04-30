package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.HashMap;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 设置任务变量命令
 *
 * @version
 */
public class SetTaskVariablesCommand implements Command<TaskEntity>
{
	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param variableMap
	 */
	public SetTaskVariablesCommand(int taskId, Map<String, Object> variableMap)
	{
		super();
		this.taskId = taskId;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		TaskEntity task = taskEntityService.loadById(taskId);

		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		variableEntityService.updateVariables(task.getInstanceId(), taskId, variableMap);

		return task;
	}
}
