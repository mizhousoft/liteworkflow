package com.liteworkflow.engine.impl.command;

import java.util.Map;

import org.springframework.util.Assert;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;

/**
 * 设置任务变量命令
 *
 * @version
 */
public class SetTaskVariablesCommand implements Command<ProcessInstance>
{
	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param variableMap
	 */
	public SetTaskVariablesCommand(String taskId, Map<String, Object> variableMap)
	{
		super();
		this.taskId = taskId;
		this.variableMap = variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		Task task = taskEntityService.getById(taskId);
		Assert.notNull(task, "Task not found, id is " + taskId);

		ProcessInstance instance = processInstanceEntityService.getById(task.getInstanceId());
		Assert.notNull(instance, "ProcessInstance not found, id is " + task.getInstanceId());

		Map<String, Object> dataMap = instance.getVariableMap();
		dataMap.putAll(variableMap);
		instance.setVariable(JsonHelper.toJson(dataMap));

		processInstanceEntityService.modifyVariable(instance);

		return instance;
	}
}
