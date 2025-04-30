package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.VariableUtils;

/**
 * 移除流程变量指令
 *
 * @version
 */
public class RemoveInstanceVariablesCommand implements Command<Map<String, Object>>
{
	/**
	 * 流程实例ID
	 */
	private int instanceId;

	/**
	 * 变量
	 */
	private Collection<String> variableNames;

	/**
	 * 构造函数
	 *
	 * @param instanceId
	 * @param variableNames
	 */
	public RemoveInstanceVariablesCommand(int instanceId, Collection<String> variableNames)
	{
		super();
		this.instanceId = instanceId;
		this.variableNames = variableNames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		Map<String, Object> valueMap = new HashMap<>(variableNames.size());

		List<Variable> list = variableEntityService.queryByTaskId(instanceId, 0);
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
