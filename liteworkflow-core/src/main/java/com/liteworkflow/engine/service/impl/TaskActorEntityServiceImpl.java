package com.liteworkflow.engine.service.impl;

import java.util.List;

import com.liteworkflow.engine.entity.TaskActor;
import com.liteworkflow.engine.mapper.TaskActorMapper;
import com.liteworkflow.engine.service.TaskActorEntityService;

/**
 * TaskActorEntityService
 *
 * @version
 */
public class TaskActorEntityServiceImpl implements TaskActorEntityService
{
	private TaskActorMapper taskActorMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(TaskActor taskActor)
	{
		taskActorMapper.save(taskActor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByTaskId(String taskId)
	{
		return taskActorMapper.deleteByTaskId(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeTaskActor(String taskId, String... actors)
	{
		for (String actorId : actors)
		{
			taskActorMapper.delete(taskId, actorId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TaskActor> getTaskActorsByTaskId(String taskId)
	{
		return taskActorMapper.getTaskActorsByTaskId(taskId);
	}

	/**
	 * 设置taskActorMapper
	 * 
	 * @param taskActorMapper
	 */
	public void setTaskActorMapper(TaskActorMapper taskActorMapper)
	{
		this.taskActorMapper = taskActorMapper;
	}

}
