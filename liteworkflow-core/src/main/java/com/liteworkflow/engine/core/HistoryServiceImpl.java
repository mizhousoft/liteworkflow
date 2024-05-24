package com.liteworkflow.engine.core;

import java.util.List;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.order.entity.HistoryOrder;
import com.liteworkflow.order.request.CCOrderPageRequest;
import com.liteworkflow.order.request.HistoryOrderPageRequest;
import com.liteworkflow.order.service.CCOrderEntityService;
import com.liteworkflow.order.service.HistoryOrderEntityService;
import com.liteworkflow.task.entity.HistoryTask;
import com.liteworkflow.task.entity.HistoryTaskActor;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.task.service.HistoryTaskEntityService;
import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.liteworkflow.workitem.service.WorkItemEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TODO
 *
 * @version
 */
public class HistoryServiceImpl implements HistoryService
{
	private HistoryTaskEntityService historyTaskEntityService;

	private HistoryOrderEntityService historyOrderEntityService;

	private CCOrderEntityService ccOrderEntityService;

	private WorkItemEntityService workItemEntityService;

	private HistoryTaskActorEntityService historyTaskActorEntityService;

	@Override
	public HistoryOrder getHistOrder(String orderId)
	{
		return historyOrderEntityService.getHistOrder(orderId);
	}

	@Override
	public List<HistoryOrder> getHistoryOrders(HistoryOrderPageRequest request)
	{
		return historyOrderEntityService.queryList(request);
	}

	@Override
	public Page<HistoryOrder> queryPageData(HistoryOrderPageRequest request)
	{
		return historyOrderEntityService.queryPageData(request);
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
	public Page<HistoryOrder> getCCWorks(CCOrderPageRequest request)
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
	 * 设置historyOrderEntityService
	 * 
	 * @param historyOrderEntityService
	 */
	public void setHistoryOrderEntityService(HistoryOrderEntityService historyOrderEntityService)
	{
		this.historyOrderEntityService = historyOrderEntityService;
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
