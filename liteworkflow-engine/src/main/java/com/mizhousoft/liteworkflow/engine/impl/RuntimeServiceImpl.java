package com.mizhousoft.liteworkflow.engine.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;
import com.mizhousoft.liteworkflow.engine.impl.command.DeleteProcessInstanceCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.GetInstanceVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.RemoveInstanceVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.SetInstanceVariablesCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.StartProcessInstanceCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.TerminateProcessInstanceCommand;

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
	public ProcessInstance startInstanceById(int processDefinitionId)
	{
		return startInstanceById(processDefinitionId, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(int processDefinitionId, String businessKey)
	{
		return startInstanceById(processDefinitionId, businessKey, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceById(int processDefinitionId, String businessKey, Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(null, processDefinitionId, businessKey, variableMap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey)
	{
		return startInstanceByKey(processDefinitionKey, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey)
	{
		return startInstanceByKey(processDefinitionKey, businessKey, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variableMap)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(processDefinitionKey, null, businessKey, variableMap));
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
	public Object removeVariable(int instanceId, String variableName)
	{
		Map<String, Object> valueMap = removeVariables(instanceId, Set.of(variableName));

		return valueMap.get(variableName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> removeVariables(int instanceId, Collection<String> variableNames)
	{
		return commandExecutor.execute(new RemoveInstanceVariablesCommand(instanceId, variableNames));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getVariables(int instanceId)
	{
		return commandExecutor.execute(new GetInstanceVariablesCommand(instanceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteInstance(int instanceId)
	{
		commandExecutor.execute(new DeleteProcessInstanceCommand(instanceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void terminateInstance(int instanceId)
	{
		commandExecutor.execute(new TerminateProcessInstanceCommand(instanceId));
	}

}
