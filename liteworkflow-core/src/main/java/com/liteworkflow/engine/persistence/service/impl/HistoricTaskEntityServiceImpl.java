package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskMapper;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 历史任务实例服务
 *
 * @version
 */
public class HistoricTaskEntityServiceImpl implements HistoricTaskEntityService
{
	/**
	 * HistoricTaskMapper
	 */
	private HistoricTaskMapper historicTaskMapper;

	/**
	 * 构造函数
	 *
	 * @param historicTaskMapper
	 */
	public HistoricTaskEntityServiceImpl(HistoricTaskMapper historicTaskMapper)
	{
		super();
		this.historicTaskMapper = historicTaskMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(HistoricTask task)
	{
		historicTaskMapper.save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(HistoricTask historicTask)
	{
		historicTaskMapper.delete(historicTask.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTask getById(int taskId)
	{
		return historicTaskMapper.findById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTask> queryByInstanceId(int instanceId)
	{
		return historicTaskMapper.findByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricTask> queryPageData(TaskPageRequest request)
	{
		long total = historicTaskMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricTask> list = historicTaskMapper.findPageData(rowOffset, request);

		Page<HistoricTask> page = PageBuilder.build(list, request, total);

		return page;
	}
}
