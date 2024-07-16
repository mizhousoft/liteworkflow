package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务实体服务
 *
 * @version
 */
public interface TaskEntityService
{
	/**
	 * 新增任务
	 * 
	 * @param task
	 */
	void addEntity(Task task);

	/**
	 * 修改任务
	 * 
	 * @param task
	 */
	void modifyEntity(Task task);

	/**
	 * 修改任务
	 * 
	 * @param task
	 */
	void deleteEntity(Task task);

	/**
	 * 根据任务ID查询
	 * 
	 * @param id
	 * @return
	 */
	Task getById(int id);

	/**
	 * 根据实例ID查询任务
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
