package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.Map;

import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.command.SetInstanceVariablesCommand;
import com.liteworkflow.engine.impl.command.StartProcessInstanceCommand;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * TODO
 *
 * @version
 */
public class RuntimeServiceImpl extends CommonServiceImpl implements RuntimeService
{
	/**
	 * 构造函数
	 *
	 * @param configuration
	 */
	public RuntimeServiceImpl(ProcessEngineConfigurationImpl configuration)
	{
		super(configuration);
	}

	/**
	 * 根据流程定义ID启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id)
	{
		return startInstanceById(id, null, null);
	}

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id, String operator)
	{
		return startInstanceById(id, operator, null);
	}

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id, String operator, Map<String, Object> args)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(null, id, null, operator, args));
	}

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name)
	{
		return startInstanceByName(name, null, null);
	}

	/**
	 * 根据流程名称、版本号启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name, String operator)
	{
		return startInstanceByName(name, operator, null);
	}

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name, String operator, Map<String, Object> args)
	{
		return commandExecutor.execute(new StartProcessInstanceCommand(name, null, null, operator, args));
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
