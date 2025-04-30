package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.HashMap;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 设置流程实例变量命令
 *
 * @version
 */
public class SetInstanceVariablesCommand implements Command<ProcessInstanceEntity>
{
	/**
	 * 流程实例ID
	 */
	private int instanceId;

	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param instanceId
	 * @param variableMap
	 */
	public SetInstanceVariablesCommand(int instanceId, Map<String, Object> variableMap)
	{
		super();
		this.instanceId = instanceId;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstanceEntity instance = processInstanceEntityService.loadById(instanceId);

		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		variableEntityService.updateVariables(instanceId, 0, variableMap);

		return instance;
	}
}
