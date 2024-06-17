package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTaskActor;

/**
 * HistoricTaskActorMapper
 *
 * @version
 */
public interface HistoricTaskActorMapper
{
	/**
	 * 迁移活动任务
	 * 
	 * @param task 历史任务对象
	 */
	void save(HistoricTaskActor taskActor);

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
	 * @return List<HistoricTaskActor> 历史任务参与者集合
	 */
	List<HistoricTaskActor> getHistTaskActorsByTaskId(String taskId);
}
