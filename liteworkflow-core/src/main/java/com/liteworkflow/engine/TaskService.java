package com.liteworkflow.engine;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.query.TaskQuery;

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
	 * @return
	 */
	List<Task> complete(int taskId);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param variableMap
	 * @return
	 */
	List<Task> complete(int taskId, Map<String, Object> variableMap);

	/**
	 * 设置任务变量
	 * 
	 * @param instanceId
	 * @param variableName
	 * @param value
	 */
	void setVariable(int taskId, String variableName, Object value);

	/**
	 * 设置任务变量
	 * 
	 * @param instanceId
	 * @param variableMap
	 */
	void setVariables(int taskId, Map<String, Object> variableMap);

	/**
	 * 创建任务查询
	 * 
	 * @return
	 */
	TaskQuery createTaskQuery();
}
