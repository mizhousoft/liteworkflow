package com.liteworkflow.task.service.impl;

import java.util.List;

import com.liteworkflow.task.entity.Task;
import com.liteworkflow.task.mapper.TaskMapper;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.task.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * TaskEntityService
 *
 * @version
 */
public class TaskEntityServiceImpl implements TaskEntityService
{
	private TaskMapper taskMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Task task)
	{
		taskMapper.save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Task task)
	{
		taskMapper.update(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Task task)
	{
		taskMapper.delete(task.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(String taskId)
	{
		return taskMapper.getTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> getNextActiveTasks(String parentTaskId)
	{
		return taskMapper.getNextActiveTasks(parentTaskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> getNextActiveTaskList(String orderId, String taskName, String parentTaskId)
	{
		return taskMapper.getNextActiveTaskList(orderId, taskName, parentTaskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryByOrderId(String orderId)
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setOrderId(orderId);

		return queryList(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryList(TaskPageRequest request)
	{
		request.setPageSize(100000);

		return taskMapper.findPageData(0, request);
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

	/**
	 * 设置taskMapper
	 * 
	 * @param taskMapper
	 */
	public void setTaskMapper(TaskMapper taskMapper)
	{
		this.taskMapper = taskMapper;
	}

}
