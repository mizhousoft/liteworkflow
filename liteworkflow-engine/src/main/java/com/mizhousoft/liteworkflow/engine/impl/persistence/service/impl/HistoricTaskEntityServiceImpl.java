package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricTaskMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.request.HistoricTaskPageRequest;

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
	public int addEntity(HistoricTaskEntity task)
	{
		return historicTaskMapper.save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(HistoricTaskEntity historicTask)
	{
		return historicTaskMapper.update(historicTask);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteEntity(HistoricTaskEntity historicTask)
	{
		return historicTaskMapper.delete(historicTask.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByInstanceId(int instanceId)
	{
		return historicTaskMapper.deleteByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTaskEntity getById(int taskId)
	{
		return historicTaskMapper.findById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTaskEntity loadById(int id)
	{
		HistoricTaskEntity historicTask = getById(id);

		Assert.notNull(historicTask, "HistoricTask not found, id is " + id);

		return historicTask;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTaskEntity> queryByInstanceId(int instanceId)
	{
		return historicTaskMapper.findByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricTaskEntity> queryPageData(HistoricTaskPageRequest request)
	{
		long total = historicTaskMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricTaskEntity> list = historicTaskMapper.findPageData(rowOffset, request);

		Page<HistoricTaskEntity> page = PageBuilder.build(list, request, total);

		return page;
	}
}
