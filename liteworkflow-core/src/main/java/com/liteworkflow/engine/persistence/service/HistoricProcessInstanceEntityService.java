package com.liteworkflow.engine.persistence.service;

import java.util.Set;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 历史流程实例实体服务
 *
 * @version
 */
public interface HistoricProcessInstanceEntityService
{
	/**
	 * 新增历史流程实例
	 * 
	 * @param historicInstance
	 */
	void addEntity(HistoricProcessInstance historicInstance);

	/**
	 * 修改历史流程实例
	 * 
	 * @param historicInstance
	 */
	void modifyEntity(HistoricProcessInstance historicInstance);

	/**
	 * 删除历史流程实例
	 * 
	 * @param historicInstance
	 */
	void deleteEntity(HistoricProcessInstance historicInstance);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	HistoricProcessInstance getByInstanceId(int instanceId);

	/**
	 * 根据流程定义ID查询流程实例ID
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	Set<Integer> queryIdsByProcessDefinitionId(int processDefinitionId);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request);
}
