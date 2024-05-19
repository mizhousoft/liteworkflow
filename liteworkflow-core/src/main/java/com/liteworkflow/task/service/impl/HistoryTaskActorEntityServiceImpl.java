package com.liteworkflow.task.service.impl;

import java.util.List;

import com.liteworkflow.task.entity.HistoryTaskActor;
import com.liteworkflow.task.mapper.HistoryTaskActorMapper;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;

/**
 * HistoryTaskActorEntityService
 *
 * @version
 */
public class HistoryTaskActorEntityServiceImpl implements HistoryTaskActorEntityService
{
	private HistoryTaskActorMapper historyTaskActorMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(HistoryTaskActor taskActor)
	{
		historyTaskActorMapper.save(taskActor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByTaskId(String taskId)
	{
		historyTaskActorMapper.deleteByTaskId(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTaskActor> getHistTaskActorsByTaskId(String taskId)
	{
		return historyTaskActorMapper.getHistTaskActorsByTaskId(taskId);
	}

	/**
	 * 设置historyTaskActorMapper
	 * 
	 * @param historyTaskActorMapper
	 */
	public void setHistoryTaskActorMapper(HistoryTaskActorMapper historyTaskActorMapper)
	{
		this.historyTaskActorMapper = historyTaskActorMapper;
	}

}
