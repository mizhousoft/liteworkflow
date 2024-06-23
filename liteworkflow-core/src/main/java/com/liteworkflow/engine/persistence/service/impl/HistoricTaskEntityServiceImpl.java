package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.HistoricTaskActor;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskMapper;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricTaskActorEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;

/**
 * HistoricTaskEntityService
 *
 * @version
 */
public class HistoricTaskEntityServiceImpl implements HistoricTaskEntityService
{
	private HistoricTaskMapper historicTaskMapper;

	private HistoricTaskActorEntityService historicTaskActorEntityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(HistoricTask task)
	{
		historicTaskMapper.save(task);
		if (task.getActorIds() != null)
		{
			for (String actorId : task.getActorIds())
			{
				if (StringUtils.isBlank(actorId))
					continue;

				HistoricTaskActor taskActor = new HistoricTaskActor();
				taskActor.setTaskId(task.getId());
				taskActor.setActorId(actorId);
				historicTaskActorEntityService.addEntity(taskActor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(HistoricTask historicTask)
	{
		historicTaskActorEntityService.deleteByTaskId(historicTask.getId());

		historicTaskMapper.delete(historicTask);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTask getByTaskId(String taskId)
	{
		return historicTaskMapper.getHistTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTask> queryByInstanceId(String instanceId)
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setInstanceId(instanceId);

		return queryList(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTask> queryList(TaskPageRequest request)
	{
		return historicTaskMapper.findList(request);
	}

	/**
	 * 设置historicTaskMapper
	 * 
	 * @param historicTaskMapper
	 */
	public void setHistoricTaskMapper(HistoricTaskMapper historicTaskMapper)
	{
		this.historicTaskMapper = historicTaskMapper;
	}

	/**
	 * 设置historicTaskActorEntityService
	 * 
	 * @param historicTaskActorEntityService
	 */
	public void setHistoricTaskActorEntityService(HistoricTaskActorEntityService historicTaskActorEntityService)
	{
		this.historicTaskActorEntityService = historicTaskActorEntityService;
	}
}
