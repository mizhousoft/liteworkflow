package com.liteworkflow.engine.persistence.order.mapper;

import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * HistoryOrderMapper
 *
 * @version
 */
public interface HistoricProcessInstanceMapper extends PageableMapper<HistoricProcessInstance, String>
{
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoricProcessInstance getHistOrder(String orderId);
}
