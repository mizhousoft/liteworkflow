package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TaskEntityService
 *
 * @version
 */
public interface TaskEntityService
{
	/**
	 * 保存任务对象
	 * 
	 * @param task 任务对象
	 */
	void addEntity(Task task);

	/**
	 * 更新任务对象
	 * 
	 * @param task 任务对象
	 */
	void modifyEntity(Task task);

	/**
	 * 删除任务、任务参与者对象
	 * 
	 * @param task 任务对象
	 */
	void deleteEntity(Task task);

	/**
	 * 根据任务id查询任务对象
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task getTask(String taskId);

	/**
	 * 根据父任务id查询所有子任务
	 * 
	 * @param parentTaskId 父任务id
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getNextActiveTasks(String parentTaskId);

	/**
	 * 根据流程实例id、任务名称获取
	 * 
	 * @param instanceId 流程实例id
	 * @param taskName 任务名称
	 * @param parentTaskId 父任务id
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getNextActiveTaskList(String instanceId, String taskName, String parentTaskId);

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	List<Task> queryByInstanceId(String instanceId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<Task> queryList(TaskPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Task> queryPageData(TaskPageRequest request);
}
