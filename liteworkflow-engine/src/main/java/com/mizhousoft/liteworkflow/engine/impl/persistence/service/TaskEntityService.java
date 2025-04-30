package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.request.TaskPageRequest;

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
	int addEntity(TaskEntity task);

	/**
	 * 修改任务
	 * 
	 * @param task
	 */
	int modifyEntity(TaskEntity task);

	/**
	 * 修改任务
	 * 
	 * @param task
	 */
	int deleteEntity(TaskEntity task);

	/**
	 * 根据任务ID查询
	 * 
	 * @param id
	 * @return
	 */
	TaskEntity getById(int id);

	/**
	 * 根据任务ID查询
	 * 
	 * @param id
	 * @return
	 */
	TaskEntity loadById(int id);

	/**
	 * 根据实例ID查询任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<TaskEntity> queryByInstanceId(int instanceId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<TaskEntity> queryByInstanceIds(Set<Integer> instanceIds);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<TaskEntity> queryPageData(TaskPageRequest request);
}
