package com.liteworkflow.engine.persistence.mapper;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * HistoricProcessInstanceMapper
 *
 * @version
 */
public interface HistoricProcessInstanceMapper extends PageableMapper<HistoricProcessInstance, String>
{
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param instanceId 历史流程实例id
	 * @return HistoricProcessInstance 历史流程实例对象
	 */
	HistoricProcessInstance getHistoricInstance(String instanceId);
}
