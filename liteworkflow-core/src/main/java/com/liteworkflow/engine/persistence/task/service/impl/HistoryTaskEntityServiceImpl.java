package com.liteworkflow.engine.persistence.task.service.impl;

import java.util.List;

import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.persistence.task.entity.HistoryTask;
import com.liteworkflow.engine.persistence.task.entity.HistoryTaskActor;
import com.liteworkflow.engine.persistence.task.mapper.HistoryTaskMapper;
import com.liteworkflow.engine.persistence.task.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.engine.persistence.task.service.HistoryTaskEntityService;

/**
 * HistoryTaskEntityService
 *
 * @version
 */
public class HistoryTaskEntityServiceImpl implements HistoryTaskEntityService
{
	private HistoryTaskMapper historyTaskMapper;

	private HistoryTaskActorEntityService historyTaskActorEntityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHistory(HistoryTask task)
	{
		historyTaskMapper.save(task);
		if (task.getActorIds() != null)
		{
			for (String actorId : task.getActorIds())
			{
				if (StringHelper.isEmpty(actorId))
					continue;

				HistoryTaskActor taskActor = new HistoryTaskActor();
				taskActor.setTaskId(task.getId());
				taskActor.setActorId(actorId);
				historyTaskActorEntityService.save(taskActor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteHistoryTask(HistoryTask historyTask)
	{
		historyTaskActorEntityService.deleteByTaskId(historyTask.getId());

		historyTaskMapper.delete(historyTask);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryTask getHistTask(String taskId)
	{
		return historyTaskMapper.getHistTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTask> queryByOrderId(String orderId)
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setOrderId(orderId);

		return queryList(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTask> queryList(TaskPageRequest request)
	{
		return historyTaskMapper.findList(request);
	}

	/**
	 * 设置historyTaskMapper
	 * 
	 * @param historyTaskMapper
	 */
	public void setHistoryTaskMapper(HistoryTaskMapper historyTaskMapper)
	{
		this.historyTaskMapper = historyTaskMapper;
	}

	/**
	 * 设置historyTaskActorEntityService
	 * 
	 * @param historyTaskActorEntityService
	 */
	public void setHistoryTaskActorEntityService(HistoryTaskActorEntityService historyTaskActorEntityService)
	{
		this.historyTaskActorEntityService = historyTaskActorEntityService;
	}
}
