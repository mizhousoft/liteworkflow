package com.liteworkflow.engine.util;

import java.time.LocalDateTime;
import java.util.Map;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.commons.json.JSONUtils;

/**
 * 流程实例工具类
 *
 * @version
 */
public abstract class ProcessInstanceUtils
{
	/**
	 * 创建流程实例
	 * 
	 * @param processDefinition
	 * @param businessKey
	 * @param initiator
	 * @param variables
	 * @param parentId
	 * @param parentNodeName
	 * @param engineConfiguration
	 * @return
	 */
	public static ProcessInstance createProcessInstance(ProcessDefinition processDefinition, String businessKey, String initiator,
	        Map<String, Object> variables, int parentId, String parentNodeName, ProcessEngineConfigurationImpl engineConfiguration)
	{
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = new ProcessInstance();
		instance.setParentId(parentId);
		instance.setProcessDefinitionId(processDefinition.getId());
		instance.setBusinessKey(businessKey);
		instance.setPriority(100);
		instance.setParentNodeName(parentNodeName);
		instance.setVariable(JSONUtils.toJSONStringQuietly(variables));
		instance.setRevision(0);
		instance.setInitiator(initiator);
		instance.setCreateTime(LocalDateTime.now());

		processInstanceEntityService.addEntity(instance);

		HistoricProcessInstanceUtils.createHistoricProcessInstance(instance, engineConfiguration);

		return instance;
	}
}
