package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.request.HistoricTaskPageRequest;

/**
 * 历史任务实例服务
 *
 * @version
 */
public interface HistoricTaskEntityService
{
	/**
	 * 新增历史任务
	 * 
	 * @param historicTask
	 */
	int addEntity(HistoricTaskEntity historicTask);

	/**
	 * 修改历史任务
	 * 
	 * @param task
	 */
	int modifyEntity(HistoricTaskEntity historicTask);

	/**
	 * 删除历史任务
	 * 
	 * @param historicTask
	 */
	int deleteEntity(HistoricTaskEntity historicTask);

	/**
	 * 根据流程实例ID删除
	 * 
	 * @param instanceId
	 */
	int deleteByInstanceId(int instanceId);

	/**
	 * 根据ID获取历史任务
	 * 
	 * @param id
	 * @return
	 */
	HistoricTaskEntity getById(int id);

	/**
	 * 根据ID获取历史任务
	 * 
	 * @param id
	 * @return
	 */
	HistoricTaskEntity loadById(int id);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTaskEntity> queryByInstanceId(int instanceId);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricTaskEntity> queryPageData(HistoricTaskPageRequest request);
}
