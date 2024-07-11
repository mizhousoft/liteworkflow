package com.liteworkflow.engine;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

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
	List<Task> complete(String taskId);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param operator
	 * @return
	 */
	List<Task> complete(String taskId, String operator);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param operator
	 * @param variableMap
	 * @return
	 */
	List<Task> complete(String taskId, String operator, Map<String, Object> variableMap);

	/**
	 * 设置任务变量
	 * 
	 * @param instanceId
	 * @param variableName
	 * @param value
	 */
	void setVariable(String taskId, String variableName, Object value);

	/**
	 * 设置任务变量
	 * 
	 * @param instanceId
	 * @param variableMap
	 */
	void setVariables(String taskId, Map<String, Object> variableMap);

	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId
	 * @return
	 */
	Task getTask(String taskId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<Task> queryByInstanceId(String instanceId);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Task> queryPageData(TaskPageRequest request);
}
