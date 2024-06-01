package com.liteworkflow.engine.persistence.order.service;

import java.util.List;

import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.order.request.ProcessInstPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * OrderEntityService
 *
 * @version
 */
public interface ProcessInstanceEntityService
{
	/**
	 * 保存流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void saveOrder(ProcessInstance order);

	/**
	 * 保存流程实例
	 * 
	 * @param order 流程实例对象
	 */
	void saveOrderAndHistory(ProcessInstance order);

	/**
	 * 更新流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void updateOrder(ProcessInstance order);

	/**
	 * 删除流程实例对象
	 * 
	 * @param order 流程实例对象
	 */
	void deleteOrder(ProcessInstance order);

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

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<ProcessInstance> queryList(ProcessInstPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessInstance> queryPageData(ProcessInstPageRequest request);
}
