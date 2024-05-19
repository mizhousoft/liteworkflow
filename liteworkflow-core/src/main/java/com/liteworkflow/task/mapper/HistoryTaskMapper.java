package com.liteworkflow.task.mapper;

import java.util.List;

import com.liteworkflow.task.entity.HistoryTask;
import com.liteworkflow.task.request.TaskPageRequest;

/**
 * HistoryTaskMapper
 *
 * @version
 */
public interface HistoryTaskMapper
{
	/**
	 * 迁移活动任务
	 * 
	 * @param task 历史任务对象
	 */
	void save(HistoryTask task);

	/**
	 * 删除历史任务记录
	 * 
	 * @param historyTask 历史任务
	 */
	void delete(HistoryTask historyTask);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId 历史任务id
	 * @return 历史任务对象
	 */
	HistoryTask getHistTask(String taskId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoryTask> findList(TaskPageRequest request);
}
