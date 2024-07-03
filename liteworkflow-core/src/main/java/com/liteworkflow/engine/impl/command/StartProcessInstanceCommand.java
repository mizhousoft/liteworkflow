package com.liteworkflow.engine.impl.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.executor.FlowExecutorFactory;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.util.ProcessInstanceUtils;

/**
 * 启动流程实例命令
 *
 * @version
 */
public class StartProcessInstanceCommand implements Command<ProcessInstance>
{
	/**
	 * 流程名称
	 */
	private String processDefinitionName;

	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;

	/**
	 * 业务标识
	 */
	private String businessKey;

	/**
	 * 操作人
	 */
	private String operator;

	/**
	 * 流程变量
	 */
	private Map<String, Object> variables;

	/**
	 * 构造函数
	 *
	 * @param processDefinitionName
	 * @param processDefinitionId
	 * @param businessKey
	 * @param operator
	 * @param variables
	 */
	public StartProcessInstanceCommand(String processDefinitionName, String processDefinitionId, String businessKey, String operator,
	        Map<String, Object> variables)
	{
		this.processDefinitionName = processDefinitionName;
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.operator = operator;
		this.variables = null == variables ? new HashMap<>(10) : variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();

		ProcessDefinition processDefinition = getProcessDefinition(engineConfiguration);

		ProcessInstance processInstance = ProcessInstanceUtils.createProcessInstance(processDefinition, businessKey, operator, variables,
		        null, null, engineConfiguration);

		Execution execution = new Execution(engineConfiguration, processDefinition, processInstance, variables);
		execution.setOperator(operator);

		StartModel startModel = processDefinition.getModel().getStartModel();

		FlowExecutor flowExecutor = FlowExecutorFactory.build(startModel);
		flowExecutor.execute(execution, startModel);

		return processInstance;
	}

	/**
	 * 获取ProcessDefinition
	 * 
	 * @param engineConfiguration
	 * @return
	 */
	private ProcessDefinition getProcessDefinition(ProcessEngineConfigurationImpl engineConfiguration)
	{
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		ProcessDefinition processDefinition = null;
		if (null != processDefinitionId)
		{
			processDefinition = repositoryService.getById(processDefinitionId);
			Assert.notNull(processDefinition, "Process definition not found, id is " + processDefinitionId);
		}
		else if (null != processDefinitionName)
		{
			processDefinition = repositoryService.getLatestByName(processDefinitionName);
			Assert.notNull(processDefinition, "Process definition not found, name is " + processDefinitionName);
		}

		return processDefinition;
	}
}
