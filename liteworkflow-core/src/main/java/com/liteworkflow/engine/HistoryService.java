package com.liteworkflow.engine;

import java.util.List;

import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.request.CCOrderPageRequest;
import com.liteworkflow.engine.persistence.order.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.task.entity.HistoryTask;
import com.liteworkflow.engine.persistence.task.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.workitem.entity.WorkItem;
import com.liteworkflow.engine.persistence.workitem.request.WorkItemPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TODO
 *
 * @version
 */
public interface HistoryService
{
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	HistoricProcessInstance getHistOrder(String orderId);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId 历史任务id
	 * @return HistoryTask 历史任务对象
	 */
	HistoryTask getHistTask(String taskId);

	/**
	 * 根据任务ID获取历史任务参与者数组
	 * 
	 * @param taskId 历史任务id
	 * @return String[] 历史参与者id数组
	 */
	String[] getHistoryTaskActorsByTaskId(String taskId);

	/**
	 * 根据filter查询历史流程实例
	 * 
	 * @param filter 查询过滤器
	 * @return List<HistoryOrder> 历史实例集合
	 */
	List<HistoricProcessInstance> getHistoryOrders(HistoricProcessInstPageRequest request);

	/**
	 * 根据filter分页查询历史流程实例
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<HistoryOrder> 历史实例集合
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request);

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
	 * 根据filter分页查询抄送工作项（包含process、order）
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 抄送工作项集合
	 */
	Page<HistoricProcessInstance> getCCWorks(CCOrderPageRequest request);

	/**
	 * 根据filter分页查询已完成的历史任务项
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 历史工作项集合
	 */
	Page<WorkItem> getHistoryWorkItems(WorkItemPageRequest request);
}
