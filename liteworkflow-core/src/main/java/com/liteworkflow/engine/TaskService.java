package com.liteworkflow.engine;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.CustomModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
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
 * @author yuqs
 * @since 1.0
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
	 * 根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点
	 * 1、nodeName为null时，则跳转至上一步处理
	 * 2、nodeName不为null时，则任意跳转，即动态创建转移
	 * 
	 * @param taskId 任务主键ID
	 * @param operator 操作人主键ID
	 * @param args 参数列表
	 * @param nodeName 跳转的节点名称
	 * @return List<Task> 任务集合
	 */
	public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName);

	/**
	 * 根据流程实例ID，操作人ID，参数列表按照节点模型model创建新的自由任务
	 * 
	 * @param instanceId 流程实例id
	 * @param operator 操作人id
	 * @param args 参数列表
	 * @param model 节点模型
	 * @return List<Task> 任务集合
	 */
	public List<Task> createFreeTask(String instanceId, String operator, Map<String, Object> args, TaskModel model);

	/**
	 * 根据任务ID获取任务对象
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task getTask(String taskId);

	/**
	 * 根据任务ID获取活动任务参与者数组
	 * 
	 * @param taskId 任务id
	 * @return String[] 参与者id数组
	 */
	String[] getTaskActorsByTaskId(String taskId);

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
	 * 完成指定的任务，删除活动任务记录，创建历史任务
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task complete(String taskId);

	/**
	 * 完成指定的任务，删除活动任务记录，创建历史任务
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人
	 * @return Task 任务对象
	 */
	Task complete(String taskId, String operator);

	/**
	 * 根据任务主键ID，操作人ID完成任务
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人id
	 * @param args 参数集合
	 * @return Task 任务对象
	 */
	Task complete(String taskId, String operator, Map<String, Object> args);

	/**
	 * 根据执行对象、自定义节点模型创建历史任务记录
	 * 
	 * @param execution 执行对象
	 * @param model 自定义节点模型
	 * @return 历史任务
	 */
	HistoricTask history(Execution execution, CustomModel model);

	/**
	 * 根据任务主键ID，操作人ID提取任务
	 * 提取任务相当于预受理操作，仅仅标识此任务只能由此操作人处理
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人id
	 * @return Task 任务对象
	 */
	Task take(String taskId, String operator);

	/**
	 * 根据历史任务主键id，操作人唤醒历史任务
	 * 该方法会导致流程状态不可控，请慎用
	 * 
	 * @param taskId 历史任务id
	 * @param operator 操作人id
	 * @return Task 唤醒后的任务对象
	 */
	Task resume(String taskId, String operator);

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

	/**
	 * 根据taskId、operator，判断操作人operator是否允许执行任务
	 * 
	 * @param task 任务对象
	 * @param operator 操作人
	 * @return boolean 是否允许操作
	 */
	boolean isAllowed(Task task, String operator);

	/**
	 * 根据任务模型、执行对象创建新的任务
	 * 
	 * @param taskModel 任务模型
	 * @param execution 执行对象
	 * @return List<Task> 创建任务集合
	 */
	List<Task> createTask(TaskModel taskModel, Execution execution);

	/**
	 * 根据已有任务id、任务类型、参与者创建新的任务
	 * 
	 * @param taskId 主办任务id
	 * @param taskType 任务类型
	 * @param actors 参与者集合
	 * @return List<Task> 创建任务集合
	 */
	List<Task> createNewTask(String taskId, int taskType, String... actors);

	/**
	 * 根据任务id获取任务模型
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	TaskModel getTaskModel(String taskId);
}
