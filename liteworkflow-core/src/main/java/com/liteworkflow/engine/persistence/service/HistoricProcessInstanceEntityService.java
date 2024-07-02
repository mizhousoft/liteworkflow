package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * HistoricProcessInstanceEntityService
 *
 * @version
 */
public interface HistoricProcessInstanceEntityService
{
	/**
	 * 迁移活动实例
	 * 
	 * @param historicInstance 历史流程实例对象
	 */
	void addEntity(HistoricProcessInstance historicInstance);

	/**
	 * 更新历史流程实例状态
	 * 
	 * @param historicInstance 历史流程实例对象
	 */
	void modifyEntity(HistoricProcessInstance historicInstance);

	/**
	 * 删除历史实例记录
	 * 
	 * @param historicInstance 历史实例
	 */
	void deleteEntity(HistoricProcessInstance historicInstance);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param instanceId 历史流程实例id
	 * @return HistoricProcessInstance 历史流程实例对象
	 */
	HistoricProcessInstance getByInstanceId(String instanceId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoricProcessInstance> queryList(HistoricInstancePageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request);
}
