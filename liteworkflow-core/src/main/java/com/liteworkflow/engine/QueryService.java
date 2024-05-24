package com.liteworkflow.engine;

import java.util.List;

import com.liteworkflow.order.entity.HistoryOrder;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.order.request.CCOrderPageRequest;
import com.liteworkflow.order.request.HistoryOrderPageRequest;
import com.liteworkflow.order.request.OrderPageRequest;
import com.liteworkflow.task.entity.HistoryTask;
import com.liteworkflow.task.entity.Task;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程相关的查询服务
 * 
 * @author yuqs
 * @since 1.0
 */
public interface QueryService
{
	/**
	 * 根据流程实例ID获取流程实例对象
	 * 
	 * @param orderId 流程实例id
	 * @return Order 流程实例对象
	 */
	Order getOrder(String orderId);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoryOrder getHistOrder(String orderId);

	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task getTask(String taskId);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId 历史任务id
	 * @return HistoryTask 历史任务对象
	 */
	HistoryTask getHistTask(String taskId);

	/**
	 * 根据任务ID获取活动任务参与者数组
	 * 
	 * @param taskId 任务id
	 * @return String[] 参与者id数组
	 */
	String[] getTaskActorsByTaskId(String taskId);

	/**
	 * 根据任务ID获取历史任务参与者数组
	 * 
	 * @param taskId 历史任务id
	 * @return String[] 历史参与者id数组
	 */
	String[] getHistoryTaskActorsByTaskId(String taskId);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getActiveTasks(String orderId);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getActiveTasks(TaskPageRequest request);

	/**
	 * 根据filter分页查询活动任务
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	Page<Task> queryPageData(TaskPageRequest request);

	/**
	 * 根据filter查询流程实例列表
	 * 
	 * @param filter 查询过滤器
	 * @return List<Order> 活动实例集合
	 */
	List<Order> getActiveOrders(OrderPageRequest request);

	/**
	 * 根据filter分页查询流程实例列表
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<Order> 活动实例集合
	 */
	Page<Order> queryPageData(OrderPageRequest request);

	/**
	 * 根据filter查询历史流程实例
	 * 
	 * @param filter 查询过滤器
	 * @return List<HistoryOrder> 历史实例集合
	 */
	List<HistoryOrder> getHistoryOrders(HistoryOrderPageRequest request);

	/**
	 * 根据filter分页查询历史流程实例
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<HistoryOrder> 历史实例集合
	 */
	Page<HistoryOrder> queryPageData(HistoryOrderPageRequest request);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<HistoryTask> getHistoryTasks(String orderId);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<HistoryTask> getHistoryTasks(TaskPageRequest request);

	/**
	 * 根据filter分页查询工作项（包含process、order、task三个实体的字段集合）
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 活动工作项集合
	 */
	Page<WorkItem> getWorkItems(WorkItemPageRequest request);

	/**
	 * 根据filter分页查询抄送工作项（包含process、order）
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 抄送工作项集合
	 */
	Page<HistoryOrder> getCCWorks(CCOrderPageRequest request);

	/**
	 * 根据filter分页查询已完成的历史任务项
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 历史工作项集合
	 */
	Page<WorkItem> getHistoryWorkItems(WorkItemPageRequest request);
}
