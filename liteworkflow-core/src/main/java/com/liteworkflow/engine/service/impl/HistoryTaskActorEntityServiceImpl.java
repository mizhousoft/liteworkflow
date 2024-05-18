package com.liteworkflow.engine.service.impl;

import java.util.List;

import com.liteworkflow.engine.entity.HistoryTaskActor;
import com.liteworkflow.engine.mapper.HistoryTaskActorMapper;
import com.liteworkflow.engine.service.HistoryTaskActorEntityService;

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
