package com.liteworkflow.engine.impl.command;

import java.util.Map;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

/**
 * TODO
 *
 * @version
 */
public class SetInstanceVariablesCommand implements Command<ProcessInstance>
{
	private String instanceId;

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
	public ProcessInstance execute(CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = processEngineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = processInstanceEntityService.getById(instanceId);
		Map<String, Object> data = instance.getVariableMap();
		data.putAll(variableMap);
		instance.setVariable(JsonHelper.toJson(data));
		processInstanceEntityService.modifyVariable(instance);

		return instance;
	}
}
