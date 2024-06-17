package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.TaskActor;

/**
 * TaskActorEntityService
 *
 * @version
 */
public interface TaskActorEntityService
{
	/**
	 * 保存任务参与者对象
	 * 
	 * @param taskActor 任务参与者对象
	 */
	void save(TaskActor taskActor);

	/**
	 * 
	 * 
	 * @param taskId
	 * @return
	 */
	int deleteByTaskId(String taskId);

	/**
	 * 删除参与者
	 * 
	 * @param taskId 任务id
	 * @param actors 参与者集合
	 */
	public void removeTaskActor(String taskId, String... actors);

	/**
	 * 根据任务id查询所有活动任务参与者集合
	 * 
	 * @param taskId 活动任务id
	 * @return List<TaskActor> 活动任务参与者集合
	 */
	List<TaskActor> getTaskActorsByTaskId(String taskId);
}
