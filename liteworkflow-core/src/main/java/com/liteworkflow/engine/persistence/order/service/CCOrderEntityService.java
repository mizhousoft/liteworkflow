package com.liteworkflow.engine.persistence.order.service;

import java.util.List;

import com.liteworkflow.engine.persistence.order.entity.CCOrder;
import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.request.CCOrderPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * CCOrderEntityService
 *
 * @version
 */
public interface CCOrderEntityService
{
	/**
	 * 保存抄送实例
	 * 
	 * @param ccorder 抄送实体
	 * @since 1.5
	 */
	void save(CCOrder ccorder);

	/**
	 * 更新抄送状态
	 * 
	 * @param ccorder 抄送实体对象
	 */
	void update(CCOrder ccorder);

	/**
	 * 删除抄送记录
	 * 
	 * @param ccorder 抄送实体对象
	 */
	void delete(CCOrder ccorder);

	/**
	 * 根据流程实例id、参与者id获取抄送记录
	 * 
	 * @param orderId 活动流程实例id
	 * @param actorIds 参与者id
	 * @return 传送记录列表
	 */
	List<CCOrder> getCCOrder(String orderId, String... actorIds);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(CCOrderPageRequest request);
}
