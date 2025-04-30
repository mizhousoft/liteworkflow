package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.TaskMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.request.TaskPageRequest;

/**
 * 任务实体服务
 *
 * @version
 */
public class TaskEntityServiceImpl implements TaskEntityService
{
	/**
	 * 任务持久层
	 */
	private TaskMapper taskMapper;

	/**
	 * 构造函数
	 *
	 * @param taskMapper
	 */
	public TaskEntityServiceImpl(TaskMapper taskMapper)
	{
		super();
		this.taskMapper = taskMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addEntity(TaskEntity task)
	{
		return taskMapper.save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(TaskEntity task)
	{
		return taskMapper.update(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteEntity(TaskEntity task)
	{
		return taskMapper.delete(task.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskEntity getById(int id)
	{
		return taskMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskEntity loadById(int id)
	{
		TaskEntity task = getById(id);

		Assert.notNull(task, "Task not found, id is " + id);

		return task;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TaskEntity> queryByInstanceId(int instanceId)
	{
		return taskMapper.findByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TaskEntity> queryByInstanceIds(Set<Integer> instanceIds)
	{
		if (CollectionUtils.isEmpty(instanceIds))
		{
			return Collections.emptyList();
		}

		return taskMapper.findByInstanceIds(instanceIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<TaskEntity> queryPageData(TaskPageRequest request)
	{
		long total = taskMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<TaskEntity> list = taskMapper.findPageData(rowOffset, request);

		Page<TaskEntity> page = PageBuilder.build(list, request, total);

		return page;
	}
}
