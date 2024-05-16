package org.snaker.engine.mapper;

import org.snaker.engine.entity.HistoryOrder;

import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * HistoryOrderMapper
 *
 * @version
 */
public interface HistoryOrderMapper extends PageableMapper<HistoryOrder, String>
{
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoryOrder getHistOrder(String orderId);
}
