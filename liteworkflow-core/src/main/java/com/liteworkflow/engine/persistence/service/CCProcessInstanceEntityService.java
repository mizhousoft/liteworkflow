package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.CCProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.request.CCInstancePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * CCProcessInstanceEntityService
 *
 * @version
 */
public interface CCProcessInstanceEntityService
{
	/**
	 * 保存抄送实例
	 * 
	 * @param ccInstance 抄送实体
	 * @since 1.5
	 */
	void addEntity(CCProcessInstance ccInstance);

	/**
	 * 更新抄送状态
	 * 
	 * @param ccInstance 抄送实体对象
	 */
	void modifyEntity(CCProcessInstance ccInstance);

	/**
	 * 删除抄送记录
	 * 
	 * @param ccInstance 抄送实体对象
	 */
	void deleteEntity(CCProcessInstance ccInstance);

	/**
	 * 根据流程实例id、参与者id获取抄送记录
	 * 
	 * @param instanceId 活动流程实例id
	 * @param actorIds 参与者id
	 * @return 传送记录列表
	 */
	List<CCProcessInstance> queryList(String instanceId, String... actorIds);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(CCInstancePageRequest request);
}
