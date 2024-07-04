package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

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
	 * @param taskId
	 * @return
	 */
	HistoricTask getById(String taskId);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryByInstanceId(String instanceId);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	List<HistoricTask> queryList(TaskPageRequest request);
}
