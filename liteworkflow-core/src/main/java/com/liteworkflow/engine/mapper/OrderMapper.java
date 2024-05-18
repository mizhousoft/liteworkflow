package com.liteworkflow.engine.mapper;

import com.liteworkflow.engine.entity.Order;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * OrderMapper
 *
 * @version
 */
public interface OrderMapper extends PageableMapper<Order, String>
{
	/**
	 * 根据流程实例id查询实例对象
	 * 
	 * @param orderId 活动流程实例id
	 * @return Order 活动流程实例对象
	 */
	Order getOrder(String orderId);

	/**
	 * 更新实例变量（包括历史实例表）
	 * 
	 * @param order 实例对象
	 */
	void updateOrderVariable(Order order);
}
