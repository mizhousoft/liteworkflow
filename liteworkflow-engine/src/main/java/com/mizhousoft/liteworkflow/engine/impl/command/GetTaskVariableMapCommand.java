package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 获取任务变量指令
 *
 * @version
 */
public class GetTaskVariableMapCommand implements Command<Map<Integer, Map<String, Object>>>
{
	/**
	 * 实例ID
	 */
	private Set<Integer> instanceIds;

	/**
	 * 任务ID
	 */
	private Set<Integer> taskIds;

	/**
	 * 构造函数
	 *
	 * @param instanceIds
	 * @param taskIds
	 */
	public GetTaskVariableMapCommand(Set<Integer> instanceIds, Set<Integer> taskIds)
	{
		super();
		this.instanceIds = instanceIds;
		this.taskIds = taskIds;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		return variableEntityService.queryMapByTaskIds(instanceIds, taskIds);
	}
}
