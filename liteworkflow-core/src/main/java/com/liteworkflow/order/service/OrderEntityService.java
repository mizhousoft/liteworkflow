package com.liteworkflow.order.service;

import java.util.List;

import com.liteworkflow.order.entity.Order;
import com.liteworkflow.order.request.OrderPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * OrderEntityService
 *
 * @version
 */
public interface OrderEntityService
{
	/**
	 * 保存流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void saveOrder(Order order);

	/**
	 * 保存流程实例
	 * 
	 * @param order 流程实例对象
	 */
	void saveOrderAndHistory(Order order);

	/**
	 * 更新流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void updateOrder(Order order);

	/**
	 * 删除流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void deleteOrder(Order order);

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

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<Order> queryList(OrderPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Order> queryPageData(OrderPageRequest request);
}
