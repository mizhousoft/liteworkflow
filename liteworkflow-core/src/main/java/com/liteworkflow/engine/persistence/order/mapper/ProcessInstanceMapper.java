package com.liteworkflow.engine.persistence.order.mapper;

import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * OrderMapper
 *
 * @version
 */
public interface ProcessInstanceMapper extends PageableMapper<ProcessInstance, String>
{
	/**
	 * 根据流程实例id查询实例对象
	 * 
	 * @param orderId 活动流程实例id
	 * @return Order 活动流程实例对象
	 */
	ProcessInstance getOrder(String orderId);

	/**
	 * 更新实例变量（包括历史实例表）
	 * 
	 * @param order 实例对象
	 */
	void updateOrderVariable(ProcessInstance order);
}
