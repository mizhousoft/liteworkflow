package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoryTaskActor;

/**
 * HistoryTaskActorEntityService
 *
 * @version
 */
public interface HistoryTaskActorEntityService
{
	/**
	 * 迁移活动任务
	 * 
	 * @param task 历史任务对象
	 */
	void save(HistoryTaskActor taskActor);

	/**
	 * 
	 * 
	 * @param taskId
	 */
	void deleteByTaskId(String taskId);

	/**
	 * 根据任务id查询所有历史任务参与者集合
	 * 
	 * @param taskId 历史任务id
	 * @return List<HistoryTaskActor> 历史任务参与者集合
	 */
	List<HistoryTaskActor> getHistTaskActorsByTaskId(String taskId);
}
