package com.liteworkflow.engine.persistence.order.service;

import java.util.List;

import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.request.HistoricProcessInstPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * HistoryOrderEntityService
 *
 * @version
 */
public interface HistoricProcessInstanceEntityService
{
	/**
	 * 迁移活动实例
	 * 
	 * @param order 历史流程实例对象
	 */
	void save(HistoricProcessInstance order);

	/**
	 * 更新历史流程实例状态
	 * 
	 * @param order 历史流程实例对象
	 */
	void update(HistoricProcessInstance order);

	/**
	 * 删除历史实例记录
	 * 
	 * @param historyOrder 历史实例
	 */
	void delete(HistoricProcessInstance historyOrder);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoricProcessInstance getHistOrder(String orderId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoricProcessInstance> queryList(HistoricProcessInstPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request);
}
