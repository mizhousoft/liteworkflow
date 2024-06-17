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
	public List<Task> getNextActiveTaskList(String instanceId, String taskName, String parentTaskId)
	{
		return taskMapper.getNextActiveTaskList(instanceId, taskName, parentTaskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryByInstanceId(String instanceId)
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setInstanceId(instanceId);

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
