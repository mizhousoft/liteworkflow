package com.liteworkflow.engine;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.WorkItem;
import com.liteworkflow.engine.persistence.request.CCOrderPageRequest;
import com.liteworkflow.engine.persistence.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.request.WorkItemPageRequest;
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
	 * @param instanceId 历史流程实例id
	 * @return HistoricProcessInstance 历史流程实例对象
	 */
	HistoricProcessInstance getHistoricInstance(String instanceId);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId 历史任务id
	 * @return HistoricTask 历史任务对象
	 */
	HistoricTask getHistTask(String taskId);

	/**
	 * 根据任务ID获取历史任务参与者数组
	 * 
	 * @param taskId 历史任务id
	 * @return String[] 历史参与者id数组
	 */
	String[] getHistoricTaskActorsByTaskId(String taskId);

	/**
	 * 根据filter查询历史流程实例
	 * 
	 * @param filter 查询过滤器
	 * @return List<HistoricProcessInstance> 历史实例集合
	 */
	List<HistoricProcessInstance> getHistoricInstances(HistoricProcessInstPageRequest request);

	/**
	 * 根据filter分页查询历史流程实例
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<HistoricProcessInstance> 历史实例集合
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<HistoricTask> getHistoricTasks(String instanceId);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<HistoricTask> getHistoricTasks(TaskPageRequest request);

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
	Page<WorkItem> getHistoricWorkItems(WorkItemPageRequest request);
}
