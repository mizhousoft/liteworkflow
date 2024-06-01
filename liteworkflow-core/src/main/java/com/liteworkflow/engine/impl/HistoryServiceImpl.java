package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.request.CCOrderPageRequest;
import com.liteworkflow.engine.persistence.order.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.order.service.CCOrderEntityService;
import com.liteworkflow.engine.persistence.order.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.task.entity.HistoryTask;
import com.liteworkflow.engine.persistence.task.entity.HistoryTaskActor;
import com.liteworkflow.engine.persistence.task.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.engine.persistence.task.service.HistoryTaskEntityService;
import com.liteworkflow.engine.persistence.workitem.entity.WorkItem;
import com.liteworkflow.engine.persistence.workitem.request.WorkItemPageRequest;
import com.liteworkflow.engine.persistence.workitem.service.WorkItemEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TODO
 *
 * @version
 */
public class HistoryServiceImpl implements HistoryService
{
	private HistoryTaskEntityService historyTaskEntityService;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	private CCOrderEntityService ccOrderEntityService;

	private WorkItemEntityService workItemEntityService;

	private HistoryTaskActorEntityService historyTaskActorEntityService;

	@Override
	public HistoricProcessInstance getHistOrder(String orderId)
	{
		return historicProcessInstanceEntityService.getHistOrder(orderId);
	}

	@Override
	public List<HistoricProcessInstance> getHistoryOrders(HistoricProcessInstPageRequest request)
	{
		return historicProcessInstanceEntityService.queryList(request);
	}

	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request)
	{
		return historicProcessInstanceEntityService.queryPageData(request);
	}

	@Override
	public HistoryTask getHistTask(String taskId)
	{
		return historyTaskEntityService.getHistTask(taskId);
	}

	@Override
	public String[] getHistoryTaskActorsByTaskId(String taskId)
	{
		List<HistoryTaskActor> actors = historyTaskActorEntityService.getHistTaskActorsByTaskId(taskId);
		if (actors == null || actors.isEmpty())
			return null;
		String[] actorIds = new String[actors.size()];
		for (int i = 0; i < actors.size(); i++)
		{
			HistoryTaskActor ta = actors.get(i);
			actorIds[i] = ta.getActorId();
		}
		return actorIds;
	}

	@Override
	public List<HistoryTask> getHistoryTasks(String orderId)
	{
		return historyTaskEntityService.queryByOrderId(orderId);
	}

	@Override
	public List<HistoryTask> getHistoryTasks(TaskPageRequest request)
	{
		return historyTaskEntityService.queryList(request);
	}

	@Override
	public Page<HistoricProcessInstance> getCCWorks(CCOrderPageRequest request)
	{
		return ccOrderEntityService.queryPageData(request);
	}

	@Override
	public Page<WorkItem> getHistoryWorkItems(WorkItemPageRequest request)
	{
		return workItemEntityService.queryHistory(request);
	}

	/**
	 * 设置historyTaskEntityService
	 * 
	 * @param historyTaskEntityService
	 */
	public void setHistoryTaskEntityService(HistoryTaskEntityService historyTaskEntityService)
	{
		this.historyTaskEntityService = historyTaskEntityService;
	}

	/**
	 * 设置historicProcessInstanceEntityService
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * 设置ccOrderEntityService
	 * 
	 * @param ccOrderEntityService
	 */
	public void setCcOrderEntityService(CCOrderEntityService ccOrderEntityService)
	{
		this.ccOrderEntityService = ccOrderEntityService;
	}

	/**
	 * 设置workItemEntityService
	 * 
	 * @param workItemEntityService
	 */
	public void setWorkItemEntityService(WorkItemEntityService workItemEntityService)
	{
		this.workItemEntityService = workItemEntityService;
	}

	/**
	 * 设置historyTaskActorEntityService
	 * 
	 * @param historyTaskActorEntityService
	 */
	public void setHistoryTaskActorEntityService(HistoryTaskActorEntityService historyTaskActorEntityService)
	{
		this.historyTaskActorEntityService = historyTaskActorEntityService;
	}

}
