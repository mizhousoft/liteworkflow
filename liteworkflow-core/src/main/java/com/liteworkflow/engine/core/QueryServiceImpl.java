package com.liteworkflow.engine.core;

import java.util.List;

import com.liteworkflow.engine.QueryService;
import com.liteworkflow.engine.entity.HistoryOrder;
import com.liteworkflow.engine.entity.HistoryTask;
import com.liteworkflow.engine.entity.HistoryTaskActor;
import com.liteworkflow.engine.entity.Order;
import com.liteworkflow.engine.entity.Task;
import com.liteworkflow.engine.entity.TaskActor;
import com.liteworkflow.engine.entity.WorkItem;
import com.liteworkflow.engine.request.CCOrderPageRequest;
import com.liteworkflow.engine.request.HistoryOrderPageRequest;
import com.liteworkflow.engine.request.OrderPageRequest;
import com.liteworkflow.engine.request.TaskPageRequest;
import com.liteworkflow.engine.request.WorkItemPageRequest;
import com.liteworkflow.engine.service.CCOrderEntityService;
import com.liteworkflow.engine.service.HistoryOrderEntityService;
import com.liteworkflow.engine.service.HistoryTaskActorEntityService;
import com.liteworkflow.engine.service.HistoryTaskEntityService;
import com.liteworkflow.engine.service.OrderEntityService;
import com.liteworkflow.engine.service.TaskActorEntityService;
import com.liteworkflow.engine.service.TaskEntityService;
import com.liteworkflow.engine.service.WorkItemEntityService;

/**
 * 查询服务实现类
 * 
 * @author yuqs
 * @since 1.0
 */
public class QueryServiceImpl extends AccessService implements QueryService
{
	private TaskEntityService taskEntityService;

	private TaskActorEntityService taskActorEntityService;

	private HistoryTaskEntityService historyTaskEntityService;

	private OrderEntityService orderEntityService;

	private HistoryOrderEntityService historyOrderEntityService;

	private HistoryTaskActorEntityService historyTaskActorEntityService;

	private CCOrderEntityService ccOrderEntityService;

	private WorkItemEntityService workItemEntityService;

	public Order getOrder(String orderId)
	{
		return orderEntityService.getOrder(orderId);
	}

	public Task getTask(String taskId)
	{
		return taskEntityService.getTask(taskId);
	}

	public String[] getTaskActorsByTaskId(String taskId)
	{
		List<TaskActor> actors = taskActorEntityService.getTaskActorsByTaskId(taskId);
		if (actors == null || actors.isEmpty())
			return null;
		String[] actorIds = new String[actors.size()];
		for (int i = 0; i < actors.size(); i++)
		{
			TaskActor ta = actors.get(i);
			actorIds[i] = ta.getActorId();
		}
		return actorIds;
	}

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

	public HistoryOrder getHistOrder(String orderId)
	{
		return historyOrderEntityService.getHistOrder(orderId);
	}

	public HistoryTask getHistTask(String taskId)
	{
		return historyTaskEntityService.getHistTask(taskId);
	}

	public List<Task> getActiveTasks(String orderId)
	{
		return taskEntityService.queryByOrderId(orderId);
	}

	public List<Task> getActiveTasks(TaskPageRequest request)
	{
		return taskEntityService.queryList(request);
	}

	public com.mizhousoft.commons.data.domain.Page<Task> queryPageData(TaskPageRequest request)
	{
		return taskEntityService.queryPageData(request);
	}

	public List<Order> getActiveOrders(OrderPageRequest request)
	{
		return orderEntityService.queryList(request);
	}

	public com.mizhousoft.commons.data.domain.Page<Order> queryPageData(OrderPageRequest request)
	{
		return orderEntityService.queryPageData(request);
	}

	public List<HistoryOrder> getHistoryOrders(HistoryOrderPageRequest request)
	{
		return historyOrderEntityService.queryList(request);
	}

	public com.mizhousoft.commons.data.domain.Page<HistoryOrder> queryPageData(HistoryOrderPageRequest request)
	{
		return historyOrderEntityService.queryPageData(request);
	}

	public List<HistoryTask> getHistoryTasks(String orderId)
	{
		return historyTaskEntityService.queryByOrderId(orderId);
	}

	public List<HistoryTask> getHistoryTasks(TaskPageRequest request)
	{
		return historyTaskEntityService.queryList(request);
	}

	public com.mizhousoft.commons.data.domain.Page<WorkItem> getWorkItems(WorkItemPageRequest request)
	{
		return workItemEntityService.queryPageData(request);
	}

	public com.mizhousoft.commons.data.domain.Page<HistoryOrder> getCCWorks(CCOrderPageRequest request)
	{
		return ccOrderEntityService.queryPageData(request);
	}

	public com.mizhousoft.commons.data.domain.Page<WorkItem> getHistoryWorkItems(WorkItemPageRequest request)
	{
		return workItemEntityService.queryHistory(request);
	}

	/**
	 * 设置taskEntityService
	 * 
	 * @param taskEntityService
	 */
	public void setTaskEntityService(TaskEntityService taskEntityService)
	{
		this.taskEntityService = taskEntityService;
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
	 * 设置orderEntityService
	 * 
	 * @param orderEntityService
	 */
	public void setOrderEntityService(OrderEntityService orderEntityService)
	{
		this.orderEntityService = orderEntityService;
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
	 * 设置taskActorEntityService
	 * 
	 * @param taskActorEntityService
	 */
	public void setTaskActorEntityService(TaskActorEntityService taskActorEntityService)
	{
		this.taskActorEntityService = taskActorEntityService;
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
}
