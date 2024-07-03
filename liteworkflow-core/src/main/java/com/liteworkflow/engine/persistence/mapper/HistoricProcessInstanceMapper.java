package com.liteworkflow.engine.persistence.mapper;

import java.util.Set;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 历史流程实例实体类持久层
 *
 * @version
 */
public interface HistoricProcessInstanceMapper extends PageableMapper<HistoricProcessInstance, String>
{
	/**
	 * 根据流程定义ID查询历史流程实例ID
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	Set<String> findIdsByProcessDefinitionId(String processDefinitionId);
}
