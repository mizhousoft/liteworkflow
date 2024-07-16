package com.liteworkflow.engine.impl.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.executor.FlowExecutorFactory;
import com.liteworkflow.engine.model.StartEventModel;
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
	 * 流程Key
	 */
	private String processDefinitionKey;

	/**
	 * 流程定义ID
	 */
	private Integer processDefinitionId;

	/**
	 * 业务标识
	 */
	private String businessKey;

	/**
	 * 发起人
	 */
	private String initiator;

	/**
	 * 流程变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param processDefinitionKey
	 * @param processDefinitionId
	 * @param businessKey
	 * @param initiator
	 * @param variableMap
	 */
	public StartProcessInstanceCommand(String processDefinitionKey, Integer processDefinitionId, String businessKey, String initiator,
	        Map<String, Object> variableMap)
	{
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.initiator = initiator;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();

		ProcessDefinition processDefinition = getProcessDefinition(engineConfiguration);

		ProcessInstance processInstance = ProcessInstanceUtils.createProcessInstance(processDefinition, businessKey, initiator, variableMap,
		        0, null, engineConfiguration);

		Execution execution = new Execution(engineConfiguration, processDefinition, processInstance, variableMap);

		StartEventModel startModel = processDefinition.getBpmnModel().getStartModel();

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
			processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
			Assert.notNull(processDefinition, "ProcessDefinition not found, id is " + processDefinitionId);
		}
		else if (null != processDefinitionKey)
		{
			processDefinition = repositoryService.getLatestByKey(processDefinitionKey);
			Assert.notNull(processDefinition, "ProcessDefinition not found, key is " + processDefinitionKey);
		}
		else
		{
			throw new WorkFlowException("processDefinitionId or processDefinitionKey cannot be null at the same time.");
		}

		return processDefinition;
	}
}
