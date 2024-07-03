package com.liteworkflow.engine.util;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.impl.ProcessInstanceEntityServiceImpl;

/**
 * 历史流程实例工具类
 *
 * @version
 */
public abstract class HistoricProcessInstanceUtils
{
	/**
	 * 创建历史流程实例
	 * 
	 * @param instance
	 * @param engineConfiguration
	 * @return
	 */
	public static HistoricProcessInstance createHistoricProcessInstance(ProcessInstance instance,
	        ProcessEngineConfigurationImpl engineConfiguration)
	{
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();

		HistoricProcessInstance historicInstance = new HistoricProcessInstance();
		historicInstance.setId(instance.getId());
		historicInstance.setParentId(instance.getParentId());
		historicInstance.setProcessDefinitionId(instance.getProcessDefinitionId());
		historicInstance.setBusinessKey(instance.getBusinessKey());
		historicInstance.setState(ProcessInstanceEntityServiceImpl.STATE_ACTIVE);
		historicInstance.setPriority(instance.getPriority());
		historicInstance.setVariable(instance.getVariable());
		historicInstance.setCreator(instance.getCreator());
		historicInstance.setCreateTime(instance.getCreateTime());

		historicProcessInstanceEntityService.addEntity(historicInstance);

		return historicInstance;
	}
}
