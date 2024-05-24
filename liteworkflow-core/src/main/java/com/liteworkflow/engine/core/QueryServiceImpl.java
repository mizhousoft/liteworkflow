package com.liteworkflow.engine.core;

import java.util.List;

import com.liteworkflow.engine.QueryService;
import com.liteworkflow.order.entity.HistoryOrder;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.order.request.CCOrderPageRequest;
import com.liteworkflow.order.request.HistoryOrderPageRequest;
import com.liteworkflow.order.request.OrderPageRequest;
import com.liteworkflow.order.service.CCOrderEntityService;
import com.liteworkflow.order.service.HistoryOrderEntityService;
import com.liteworkflow.order.service.OrderEntityService;
import com.liteworkflow.task.entity.HistoryTask;
import com.liteworkflow.task.entity.HistoryTaskActor;
import com.liteworkflow.task.entity.Task;
import com.liteworkflow.task.entity.TaskActor;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.task.service.HistoryTaskEntityService;
import com.liteworkflow.task.service.TaskActorEntityService;
import com.liteworkflow.task.service.TaskEntityService;
import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.liteworkflow.workitem.service.WorkItemEntityService;

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
