package com.liteworkflow.engine.persistence.task.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.task.entity.TaskActor;

/**
 * TaskActorMapper
 *
 * @version
 */
public interface TaskActorMapper
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
	 * @param actorId
	 */
	void delete(String taskId, String actorId);

	/**
	 * 
	 * 
	 * @param taskId
	 * @return
	 */
	int deleteByTaskId(String taskId);

	/**
	 * 根据任务id查询所有活动任务参与者集合
	 * 
	 * @param taskId 活动任务id
	 * @return List<TaskActor> 活动任务参与者集合
	 */
	List<TaskActor> getTaskActorsByTaskId(String taskId);
}
