package com.liteworkflow.engine.persistence.mapper;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
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
	 * @param instanceId 活动流程实例id
	 * @return Order 活动流程实例对象
	 */
	ProcessInstance getOrder(String instanceId);

	/**
	 * 更新实例变量（包括历史实例表）
	 * 
	 * @param order 实例对象
	 */
	void updateOrderVariable(ProcessInstance order);
}
