package com.mizhousoft.liteworkflow.engine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.domain.HistoricProcessInstance;
import com.mizhousoft.liteworkflow.engine.domain.HistoricTask;
import com.mizhousoft.liteworkflow.engine.request.HistoricInstancePageRequest;
import com.mizhousoft.liteworkflow.engine.request.HistoricTaskPageRequest;

/**
 * 历史数据服务
 *
 * @version
 */
public interface HistoryService
{
	/**
	 * 删除历史实例
	 * 
	 * @param instanceId
	 */
	void deleteHistoricInstance(int instanceId);

	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	HistoricProcessInstance getHistoricInstance(int instanceId);

	/**
	 * 查询历史流程
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<HistoricProcessInstance> queryHistoricInstances(Set<Integer> instanceIds);

	/**
	 * 分页查询历史流程实例
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricTaskPageRequest request);

	/**
	 * 删除历史任务
	 * 
	 * @param taskId
	 */
	void deleteHistoricTask(int taskId);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId
	 * @return
	 */
	HistoricTask getHistoricTask(int taskId);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryHistoricTasks(int instanceId);

	/**
	 * 获取流程变量
	 * 
	 * @param instanceId
	 * @return
	 */
	Map<String, Object> getHistoricInstanceVariableMap(int instanceId);

	/**
	 * 获取流程变量
	 * 
	 * @param instanceId
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> getHistoricTaskVariableMap(int instanceId, Set<Integer> taskIds);

	/**
	 * 获取流程变量
	 * 
	 * @param instanceIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> getHistoricInstanceVariableMap(Set<Integer> instanceIds);

	/**
	 * 获取流程变量
	 * 
	 * @param instanceIds
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> getHistoricTaskVariableMap(Set<Integer> instanceIds, Set<Integer> taskIds);

}
