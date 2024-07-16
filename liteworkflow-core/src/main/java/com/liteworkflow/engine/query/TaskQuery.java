package com.liteworkflow.engine.query;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务查询
 *
 * @version
 */
public interface TaskQuery
{
	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId
	 * @return
	 */
	Task getTask(int taskId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<Task> queryByInstanceId(int instanceId);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Task> queryPageData(TaskPageRequest request);
}
