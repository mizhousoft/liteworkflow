package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 历史数据服务
 *
 * @version
 */
public class HistoryServiceImpl implements HistoryService
{
	/**
	 * HistoricTaskEntityService
	 */
	private HistoricTaskEntityService historicTaskEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * 构造函数
	 *
	 * @param historicTaskEntityService
	 * @param historicProcessInstanceEntityService
	 */
	public HistoryServiceImpl(HistoricTaskEntityService historicTaskEntityService,
	        HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		super();
		this.historicTaskEntityService = historicTaskEntityService;
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricProcessInstance getHistoricInstance(String instanceId)
	{
		return historicProcessInstanceEntityService.getByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request)
	{
		return historicProcessInstanceEntityService.queryPageData(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTask getHistoricTask(String taskId)
	{
		return historicTaskEntityService.getById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTask> queryHistoricTasks(String instanceId)
	{
		return historicTaskEntityService.queryByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricTask> queryPageData(TaskPageRequest request)
	{
		return historicTaskEntityService.queryPageData(request);
	}
}
