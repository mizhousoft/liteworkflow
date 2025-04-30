package com.mizhousoft.liteworkflow.engine;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.domain.Task;
import com.mizhousoft.liteworkflow.engine.request.TaskPageRequest;

/**
 * 任务服务
 *
 * @version
 */
public interface TaskService
{
	/**
	 * 完成任务
	 * 
	 * @param taskId
	 */
	void complete(int taskId);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param variableMap
	 */
	void complete(int taskId, Map<String, Object> variableMap);

	/**
	 * 拒绝任务
	 * 
	 * @param taskId
	 */
	void refuse(int taskId);

	/**
	 * 拒绝任务
	 * 
	 * @param taskId
	 * @param variableMap
	 */
	void refuse(int taskId, Map<String, Object> variableMap);

	/**
	 * 转交任务
	 * 
	 * @param taskId
	 * @param userId
	 * @param variableMap
	 */
	void transfer(int taskId, String userId, Map<String, Object> variableMap);

	/**
	 * 回退至活动节点
	 * 
	 * @param taskIds
	 * @param activityId
	 * @param variableMap
	 */
	void backToActivityId(Set<Integer> taskIds, String activityId, Map<Integer, Map<String, Object>> variableMap);

	/**
	 * 设置任务变量
	 * 
	 * @param taskId
	 * @param variableName
	 * @param value
	 */
	void setVariable(int taskId, String variableName, Object value);

	/**
	 * 设置任务变量
	 * 
	 * @param taskId
	 * @param variableMap
	 */
	void setVariables(int taskId, Map<String, Object> variableMap);

	/**
	 * 移除任务变量
	 * 
	 * @param taskId
	 * @param variableName
	 */
	Object removeVariable(int taskId, String variableName);

	/**
	 * 移除任务变量
	 * 
	 * @param taskId
	 * @param variableNames
	 */
	Map<String, Object> removeVariables(int taskId, Collection<String> variableNames);

	/**
	 * 获取任务变量
	 * 
	 * @param taskId
	 * @return
	 */
	Map<String, Object> getVariableMap(int taskId);

	/**
	 * 根据任务ID查询任务变量
	 * 
	 * @param instanceIds
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> queryVariableMap(Set<Integer> instanceIds, Set<Integer> taskIds);

	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId
	 * @return
	 */
	Task getTask(int taskId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<Task> queryTaskList(int instanceId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<Task> queryTaskList(Set<Integer> instanceIds);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceIds
	 * @return
	 */
	Map<Integer, List<Task>> queryTaskMap(Set<Integer> instanceIds);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Task> queryPageData(TaskPageRequest request);
}
