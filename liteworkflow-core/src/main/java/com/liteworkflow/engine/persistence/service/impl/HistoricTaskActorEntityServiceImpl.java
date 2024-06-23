package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTaskActor;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskActorMapper;
import com.liteworkflow.engine.persistence.service.HistoricTaskActorEntityService;

/**
 * HistoricTaskActorEntityService
 *
 * @version
 */
public class HistoricTaskActorEntityServiceImpl implements HistoricTaskActorEntityService
{
	private HistoricTaskActorMapper historicTaskActorMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(HistoricTaskActor taskActor)
	{
		historicTaskActorMapper.save(taskActor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByTaskId(String taskId)
	{
		historicTaskActorMapper.deleteByTaskId(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTaskActor> queryByTaskId(String taskId)
	{
		return historicTaskActorMapper.getHistTaskActorsByTaskId(taskId);
	}

	/**
	 * 设置historicTaskActorMapper
	 * 
	 * @param historicTaskActorMapper
	 */
	public void setHistoricTaskActorMapper(HistoricTaskActorMapper historicTaskActorMapper)
	{
		this.historicTaskActorMapper = historicTaskActorMapper;
	}
}
