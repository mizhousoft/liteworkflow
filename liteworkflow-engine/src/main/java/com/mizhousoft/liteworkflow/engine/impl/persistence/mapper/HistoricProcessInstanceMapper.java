package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.Set;

import com.mizhousoft.commons.mapper.PageableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;

/**
 * 历史流程实例实体类持久层
 *
 * @version
 */
public interface HistoricProcessInstanceMapper extends PageableMapper<HistoricInstanceEntity, HistoricInstanceEntity, Integer>
{
	/**
	 * 根据流程定义ID查询历史流程实例ID
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	Set<Integer> findIdsByProcessDefinitionId(int processDefinitionId);
}
