package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.VariableUtils;

/**
 * 移除任务变量指令
 *
 * @version
 */
public class RemoveTaskVariablesCommand implements Command<Map<String, Object>>
{
	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 变量
	 */
	private Collection<String> variableNames;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param variableNames
	 */
	public RemoveTaskVariablesCommand(int taskId, Collection<String> variableNames)
	{
		super();
		this.taskId = taskId;
		this.variableNames = variableNames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		TaskEntity task = taskEntityService.loadById(taskId);

		Map<String, Object> valueMap = new HashMap<>(variableNames.size());

		List<Variable> list = variableEntityService.queryByTaskId(task.getInstanceId(), taskId);
		for (Variable variable : list)
		{
			if (variableNames.contains(variable.getName()))
			{
				variableEntityService.deleteById(variable.getId());

				Object value = VariableUtils.convertValue(variable.getType(), variable.getValue());
				valueMap.put(variable.getName(), value);
			}
		}

		return valueMap;
	}
}
