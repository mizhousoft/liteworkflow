package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.Map;

import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.SetInstanceOwnerCommand;
import com.liteworkflow.engine.impl.command.SetInstanceVariablesCommand;
import com.liteworkflow.engine.impl.command.StartProcessInstanceCommand;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * 流程运行时服务
 *
 * @version
 */
public class RuntimeServiceImpl extends CommonServiceImpl implements RuntimeService
{
	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public RuntimeServiceImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super(engineConfiguration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(String processDefinitionId)
	{
		return startInstanceById(processDefinitionId, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(String processDefinitionId, String operator)
	{
		return startInstanceById(processDefinitionId, operator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(String processDefinitionId, String operator, Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(null, processDefinitionId, null, operator, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByName(String processDefinitionName)
	{
		return startInstanceByName(processDefinitionName, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByName(String processDefinitionName, String operator)
	{
		return startInstanceByName(processDefinitionName, operator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByName(String processDefinitionName, String operator, Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(processDefinitionName, null, null, operator, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOwner(String instanceId, String owner)
	{
		commandExecutor.execute(new SetInstanceOwnerCommand(instanceId, owner));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariable(String instanceId, String variableName, Object value)
	{
		Map<String, Object> variableMap = new HashMap<>(1);
		variableMap.put(variableName, value);

		setVariables(instanceId, variableMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariables(String instanceId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new SetInstanceVariablesCommand(instanceId, variableMap));
	}
}
