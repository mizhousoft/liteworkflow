package org.snaker.engine.service;

import java.util.List;

import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.request.HistoryOrderPageRequest;

import com.mizhousoft.commons.data.domain.Page;

/**
 * HistoryOrderEntityService
 *
 * @version
 */
public interface HistoryOrderEntityService
{
	/**
	 * 迁移活动实例
	 * 
	 * @param order 历史流程实例对象
	 */
	void save(HistoryOrder order);

	/**
	 * 更新历史流程实例状态
	 * 
	 * @param order 历史流程实例对象
	 */
	void update(HistoryOrder order);

	/**
	 * 删除历史实例记录
	 * 
	 * @param historyOrder 历史实例
	 */
	void delete(HistoryOrder historyOrder);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoryOrder getHistOrder(String orderId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoryOrder> queryList(HistoryOrderPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoryOrder> queryPageData(HistoryOrderPageRequest request);
}
