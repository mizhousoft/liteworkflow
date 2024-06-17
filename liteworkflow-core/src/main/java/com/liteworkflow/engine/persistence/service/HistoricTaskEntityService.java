package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

/**
 * HistoricTaskEntityService
 *
 * @version
 */
public interface HistoricTaskEntityService
{
	/**
	 * 迁移活动任务
	 * 
	 * @param historicTask 历史任务对象
	 */
	void saveEntity(HistoricTask historicTask);

	/**
	 * 删除历史任务记录
	 * 
	 * @param historicTask 历史任务
	 */
	void deleteEntity(HistoricTask historicTask);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId 历史任务id
	 * @return 历史任务对象
	 */
	HistoricTask getHistTask(String taskId);

	/**
	 * 
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryByInstanceId(String instanceId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoricTask> queryList(TaskPageRequest request);
}
