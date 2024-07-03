package com.liteworkflow.engine.impl.command;

import java.util.Map;

import org.springframework.util.Assert;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

/**
 * 设置流程实例变量命令
 *
 * @version
 */
public class SetInstanceVariablesCommand implements Command<ProcessInstance>
{
	/**
	 * 流程实例ID
	 */
	private String instanceId;

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
	public SetInstanceVariablesCommand(String instanceId, Map<String, Object> variableMap)
	{
		super();
		this.instanceId = instanceId;
		this.variableMap = variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = processInstanceEntityService.getById(instanceId);
		Assert.notNull(instance, "ProcessInstance not found, id is " + instanceId);

		Map<String, Object> dataMap = instance.getVariableMap();
		dataMap.putAll(variableMap);
		instance.setVariable(JsonHelper.toJson(dataMap));

		processInstanceEntityService.modifyVariable(instance);

		return instance;
	}
}
