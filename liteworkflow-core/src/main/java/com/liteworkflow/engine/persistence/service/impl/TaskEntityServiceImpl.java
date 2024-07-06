package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.mapper.TaskMapper;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

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
	public void addEntity(Task task)
	{
		taskMapper.save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(Task task)
	{
		taskMapper.update(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(Task task)
	{
		taskMapper.delete(task.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getById(String taskId)
	{
		return taskMapper.findById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryByInstanceId(String instanceId)
	{
		return taskMapper.findByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		long total = taskMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<Task> list = taskMapper.findPageData(rowOffset, request);

		Page<Task> page = PageBuilder.build(list, request, total);

		return page;
	}
}
