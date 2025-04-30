package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 获取流程变量指令
 *
 * @version
 */
public class GetInstanceVariablesCommand implements Command<Map<String, Object>>
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
	public GetInstanceVariablesCommand(int instanceId)
	{
		super();
		this.instanceId = instanceId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstanceEntity instance = processInstanceEntityService.loadById(instanceId);

		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();

		return variableEntityService.queryMapByTaskId(instance.getId(), 0);
	}
}
