package com.liteworkflow.engine.util;

import java.time.LocalDateTime;
import java.util.Map;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

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
	 * @param operator
	 * @param variables
	 * @param parentId
	 * @param parentNodeName
	 * @param engineConfiguration
	 * @return
	 */
	public static ProcessInstance createProcessInstance(ProcessDefinition processDefinition, String businessKey, String operator,
	        Map<String, Object> variables, String parentId, String parentNodeName, ProcessEngineConfigurationImpl engineConfiguration)
	{
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = new ProcessInstance();
		instance.setId(StringHelper.getPrimaryKey());
		instance.setParentId(parentId);
		instance.setProcessDefinitionId(processDefinition.getId());
		instance.setBusinessKey(businessKey);
		instance.setPriority(0);
		instance.setParentNodeName(parentNodeName);
		instance.setVariable(JsonHelper.toJson(variables));
		instance.setRevision(0);
		instance.setCreator(operator);
		instance.setCreateTime(LocalDateTime.now());

		processInstanceEntityService.addEntity(instance);

		HistoricProcessInstanceUtils.createHistoricProcessInstance(instance, engineConfiguration);

		return instance;
	}
}
