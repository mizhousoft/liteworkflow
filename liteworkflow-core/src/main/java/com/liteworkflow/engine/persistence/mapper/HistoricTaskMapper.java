package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

/**
 * HistoricTaskMapper
 *
 * @version
 */
public interface HistoricTaskMapper
{
	/**
	 * 迁移活动任务
	 * 
	 * @param historicTask 历史任务对象
	 */
	void save(HistoricTask historicTask);

	/**
	 * 删除历史任务记录
	 * 
	 * @param historicTask 历史任务
	 */
	void delete(HistoricTask historicTask);

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
	 * @param request
	 * @return
	 */
	List<HistoricTask> findList(TaskPageRequest request);
}
