package com.mizhousoft.liteworkflow.engine.impl.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.engine.Authentication;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.InstanceStatusEnum;
import com.mizhousoft.liteworkflow.engine.delegate.ExecutionListener;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 启动流程实例命令
 *
 * @version
 */
public class StartProcessInstanceCommand implements Command<ProcessInstanceEntity>
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
	 * 流程变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param processDefinitionKey
	 * @param processDefinitionId
	 * @param businessKey
	 * @param variableMap
	 */
	public StartProcessInstanceCommand(String processDefinitionKey, Integer processDefinitionId, String businessKey,
	        Map<String, Object> variableMap)
	{
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.variableMap = null == variableMap ? new HashMap<>(0) : variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();

		ProcessDefEntity processDefinition = getProcessDefinition(engineConfiguration);

		BpmnModel bpmnModel = processDefinition.getBpmnModel();

		StartEventModel startModel = bpmnModel.getStartModel();
		String initiatorVariableName = startModel.getInitiator();
		if (null != initiatorVariableName)
		{
			String authenticatedUserId = Authentication.getAuthenticatedUserId();
			if (null != authenticatedUserId)
			{
				variableMap.put(initiatorVariableName, authenticatedUserId);
			}
		}

		ProcessInstanceEntity processInstance = createProcessInstance(processDefinition, businessKey, 0, engineConfiguration);

		createHistoricProcessInstance(processInstance, engineConfiguration);

		variableEntityService.addVariables(processInstance.getId(), 0, variableMap);

		listenerInvocation.executeExecutionListeners(bpmnModel, processInstance, ExecutionListener.EVENTNAME_START);

		Execution execution = new Execution(engineConfiguration, processDefinition, processInstance, variableMap);

		ActivityBehavior flowExecutor = engineConfiguration.getActivityBehaviorFactory().build(startModel);
		flowExecutor.execute(execution);

		return processInstance;
	}

	/**
	 * 获取ProcessDefinition
	 * 
	 * @param engineConfiguration
	 * @return
	 */
	private ProcessDefEntity getProcessDefinition(ProcessEngineConfigurationImpl engineConfiguration)
	{
		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		ProcessDefEntity processDefinition = null;
		if (null != processDefinitionId)
		{
			processDefinition = (ProcessDefEntity) repositoryService.getProcessDefinition(processDefinitionId);
			Assert.notNull(processDefinition, "ProcessDefinition not found, processDefinitionId is " + processDefinitionId);
		}
		else if (null != processDefinitionKey)
		{
			processDefinition = (ProcessDefEntity) repositoryService.getLatestByKey(processDefinitionKey);
			Assert.notNull(processDefinition, "ProcessDefinition not found, processDefinitionKey is " + processDefinitionKey);
		}
		else
		{
			throw new WorkFlowException("processDefinitionId or processDefinitionKey cannot be null at the same time.");
		}

		return processDefinition;
	}

	/**
	 * 创建流程实例
	 * 
	 * @param processDefinition
	 * @param businessKey
	 * @param parentId
	 * @param engineConfiguration
	 * @return
	 */
	private ProcessInstanceEntity createProcessInstance(ProcessDefEntity processDefinition, String businessKey, int parentId,
	        ProcessEngineConfigurationImpl engineConfiguration)
	{
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstanceEntity instance = new ProcessInstanceEntity();
		instance.setParentId(parentId);
		instance.setProcessDefinitionId(processDefinition.getId());
		instance.setBusinessKey(businessKey);
		instance.setPriority(100);
		instance.setRevision(0);
		instance.setCreateTime(LocalDateTime.now());

		String startUserId = Authentication.getAuthenticatedUserId();
		instance.setStartUserId(startUserId);

		processInstanceEntityService.addEntity(instance);

		return instance;
	}

	/**
	 * 创建历史流程实例
	 * 
	 * @param instance
	 * @param engineConfiguration
	 * @return
	 */
	private HistoricInstanceEntity createHistoricProcessInstance(ProcessInstanceEntity instance,
	        ProcessEngineConfigurationImpl engineConfiguration)
	{
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();

		HistoricInstanceEntity historicInstance = new HistoricInstanceEntity();
		historicInstance.setId(instance.getId());
		historicInstance.setParentId(instance.getParentId());
		historicInstance.setProcessDefinitionId(instance.getProcessDefinitionId());
		historicInstance.setBusinessKey(instance.getBusinessKey());
		historicInstance.setPriority(instance.getPriority());
		historicInstance.setStartUserId(instance.getStartUserId());
		historicInstance.setStartTime(instance.getCreateTime());
		historicInstance.setStatus(InstanceStatusEnum.RUNNING.getValue());

		historicProcessInstanceEntityService.addEntity(historicInstance);

		return historicInstance;
	}
}
