package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

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
	void addEntity(HistoricTask historicTask);

	/**
	 * 删除历史任务
	 * 
	 * @param historicTask
	 */
	void deleteEntity(HistoricTask historicTask);

	/**
	 * 根据ID获取历史任务
	 * 
	 * @param id
	 * @return
	 */
	HistoricTask getById(int id);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryByInstanceId(int instanceId);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricTask> queryPageData(TaskPageRequest request);
}
