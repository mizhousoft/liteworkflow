package org.snaker.engine.service;

import java.util.List;

import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.request.TaskPageRequest;

/**
 * HistoryTaskEntityService
 *
 * @version
 */
public interface HistoryTaskEntityService
{
	/**
	 * 迁移活动任务
	 * 
	 * @param task 历史任务对象
	 */
	void saveHistory(HistoryTask task);

	/**
	 * 删除历史任务记录
	 * 
	 * @param historyTask 历史任务
	 */
	void deleteHistoryTask(HistoryTask historyTask);

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
	 * @param orderId
	 * @return
	 */
	List<HistoryTask> queryByOrderId(String orderId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoryTask> queryList(TaskPageRequest request);
}
