package com.liteworkflow.engine;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务业务类，包括以下服务：
 * 1、创建任务
 * 2、添加、删除参与者
 * 3、完成任务
 * 4、撤回任务
 * 5、回退任务
 * 6、提取任务
 * 
 * @version
 */
public interface TaskService
{
	/**
	 * 根据任务主键ID执行任务
	 * 
	 * @param taskId 任务主键ID
	 * @return List<Task> 任务集合
	 * @see #executeTask(String, String, Map)
	 */
	public List<Task> executeTask(String taskId);

	/**
	 * 根据任务主键ID，操作人ID执行任务
	 * 
	 * @param taskId 任务主键ID
	 * @param operator 操作人主键ID
	 * @return List<Task> 任务集合
	 * @see #executeTask(String, String, Map)
	 */
	public List<Task> executeTask(String taskId, String operator);

	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务
	 * 
	 * @param taskId 任务主键ID
	 * @param operator 操作人主键ID
	 * @param args 参数列表
	 * @return List<Task> 任务集合
	 */
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args);

	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task getTask(String taskId);

	/**
	 * 根据filter查询活动任务
	 * 
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getActiveTasks(String instanceId);

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
	 * 向指定的任务id添加参与者
	 * 
	 * @param taskId 任务id
	 * @param actors 参与者
	 */
	void addTaskActor(String taskId, String... actors);

	/**
	 * 向指定的任务id添加参与者
	 * 
	 * @param taskId 任务id
	 * @param performType 参与类型
	 * @param actors 参与者
	 */
	void addTaskActor(String taskId, Integer performType, String... actors);

	/**
	 * 对指定的任务id删除参与者
	 * 
	 * @param taskId 任务id
	 * @param actors 参与者
	 */
	void removeTaskActor(String taskId, String... actors);

	/**
	 * 根据任务主键id、操作人撤回任务
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人
	 * @return Task 任务对象
	 */
	Task withdrawTask(String taskId, String operator);

	/**
	 * 根据当前任务对象驳回至上一步处理
	 * 
	 * @param model 流程定义模型，用以获取上一步模型对象
	 * @param currentTask 当前任务对象
	 * @return Task 任务对象
	 */
	Task rejectTask(ProcessModel model, Task currentTask);
}
