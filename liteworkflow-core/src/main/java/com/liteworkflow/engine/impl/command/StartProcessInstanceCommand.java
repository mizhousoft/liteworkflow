package com.liteworkflow.engine.impl.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.impl.executor.ExecutorBuilder;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

/**
 * TODO
 *
 * @version
 */
public class StartProcessInstanceCommand implements Command<ProcessInstance>
{
	protected String processDefinitionKey;

	protected String processDefinitionId;

	protected String businessKey;

	protected String operator;

	protected Map<String, Object> variables;

	public StartProcessInstanceCommand(String processDefinitionKey, String processDefinitionId, String businessKey, String operator,
	        Map<String, Object> variables)
	{
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.operator = operator;
		this.variables = variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext commandContext)
	{
		if (variables == null)
		{
			variables = new HashMap<String, Object>(0);
		}

		ProcessDefinition process = getProcessDefinition(commandContext);

		return startProcess(process, operator, variables, commandContext);
	}

	private ProcessDefinition getProcessDefinition(CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

		ProcessDefinition process = null;
		if (null != processDefinitionId)
		{
			process = repositoryService.getById(processDefinitionId);
			Assert.notNull(process, "Process definition not found, name is " + processDefinitionId);
		}
		else if (null != processDefinitionKey)
		{
			process = repositoryService.getLatestByName(processDefinitionKey);
			Assert.notNull(process, "Process definition not found, name is " + processDefinitionKey);
		}

		return process;
	}

	private ProcessInstance startProcess(ProcessDefinition process, String operator, Map<String, Object> args,
	        CommandContext commandContext)
	{
		Execution execution = execute(process, operator, args, commandContext);

		StartModel startModel = process.getModel().getStartModel();

		Executor executor = ExecutorBuilder.build(startModel);
		executor.execute(execution, startModel);

		return execution.getProcessInstance();
	}

	/**
	 * 创建流程实例，并返回执行对象
	 * 
	 * @param process 流程定义
	 * @param operator 操作人
	 * @param args 参数列表
	 * @param parentId 父流程实例id
	 * @param parentNodeName 启动子流程的父流程节点名称
	 * @return Execution
	 */
	private Execution execute(ProcessDefinition process, String operator, Map<String, Object> args, CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();

		ProcessInstance instance = createInstance(process, operator, args, null, null, commandContext);

		Execution current = new Execution(processEngineConfiguration, process, instance, args);
		current.setOperator(operator);

		return current;
	}

	public ProcessInstance createInstance(ProcessDefinition process, String operator, Map<String, Object> args, String parentId,
	        String parentNodeName, CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = processEngineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = new ProcessInstance();
		instance.setId(StringHelper.getPrimaryKey());
		instance.setParentId(parentId);
		instance.setParentNodeName(parentNodeName);
		instance.setCreateTime(LocalDateTime.now());
		instance.setCreator(operator);
		instance.setProcessDefinitionId(process.getId());
		instance.setVariable(JsonHelper.toJson(args));
		processInstanceEntityService.saveInstanceAndHistoric(instance);
		return instance;
	}
}
