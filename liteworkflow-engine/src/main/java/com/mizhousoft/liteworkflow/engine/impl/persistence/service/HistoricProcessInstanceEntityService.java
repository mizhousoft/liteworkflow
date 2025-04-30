package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.request.HistoricInstancePageRequest;

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
	int addEntity(HistoricInstanceEntity historicInstance);

	/**
	 * 修改历史流程实例
	 * 
	 * @param historicInstance
	 */
	int modifyEntity(HistoricInstanceEntity historicInstance);

	/**
	 * 删除历史流程实例
	 * 
	 * @param historicInstance
	 */
	int deleteEntity(HistoricInstanceEntity historicInstance);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param id
	 * @return
	 */
	HistoricInstanceEntity getById(int id);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param id
	 * @return
	 */
	HistoricInstanceEntity loadById(int id);

	/**
	 * 根据ID查询
	 * 
	 * @param ids
	 * @return
	 */
	List<HistoricInstanceEntity> queryByIds(Set<Integer> ids);

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
	Page<HistoricInstanceEntity> queryPageData(HistoricInstancePageRequest request);
}
