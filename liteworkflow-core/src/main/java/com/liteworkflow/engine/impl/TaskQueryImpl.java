package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.query.TaskQuery;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务查询
 *
 * @version
 */
public class TaskQueryImpl implements TaskQuery
{
	/**
	 * 任务实体服务
	 */
	private TaskEntityService taskEntityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(int taskId)
	{
		return taskEntityService.getById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> queryByInstanceId(int instanceId)
	{
		return taskEntityService.queryByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		return taskEntityService.queryPageData(request);
	}

	/**
	 * 构造函数
	 *
	 * @param taskEntityService
	 */
	public TaskQueryImpl(TaskEntityService taskEntityService)
	{
		super();
		this.taskEntityService = taskEntityService;
	}
}
