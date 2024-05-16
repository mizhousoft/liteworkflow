package org.snaker.engine.service.impl;

import java.util.List;

import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.HistoryTaskActor;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.mapper.HistoryTaskMapper;
import org.snaker.engine.request.TaskPageRequest;
import org.snaker.engine.service.HistoryTaskActorEntityService;
import org.snaker.engine.service.HistoryTaskEntityService;

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
