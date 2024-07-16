package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.Map;

import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.DeleteProcessInstanceCommand;
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
	public ProcessInstance startInstanceById(int processDefinitionId, String initiator)
	{
		return startInstanceById(processDefinitionId, null, initiator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(int processDefinitionId, String businessKey, String initiator)
	{
		return startInstanceById(processDefinitionId, businessKey, initiator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(int processDefinitionId, String businessKey, String initiator, Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(null, processDefinitionId, businessKey, initiator, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey, String initiator)
	{
		return startInstanceByKey(processDefinitionKey, null, initiator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, String initiator)
	{
		return startInstanceByKey(processDefinitionKey, businessKey, initiator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, String initiator,
	        Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(processDefinitionKey, null, businessKey, initiator, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOwner(int instanceId, String owner)
	{
		commandExecutor.execute(new SetInstanceOwnerCommand(instanceId, owner));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariable(int instanceId, String variableName, Object value)
	{
		Map<String, Object> variableMap = new HashMap<>(1);
		variableMap.put(variableName, value);

		setVariables(instanceId, variableMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVariables(int instanceId, Map<String, Object> variableMap)
	{
		commandExecutor.execute(new SetInstanceVariablesCommand(instanceId, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteInstance(int instanceId)
	{
		commandExecutor.execute(new DeleteProcessInstanceCommand(instanceId));
	}
}
