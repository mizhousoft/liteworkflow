package com.liteworkflow.engine;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * 任务监听器
 *
 * @version
 */
public abstract class TaskListener
{
	/**
	 * 创建任务事件
	 */
	String EVENTNAME_CREATE = "create";

	/**
	 * 分配参与者任务事件
	 */
	String EVENTNAME_ASSIGNMENT = "assignment";

	/**
	 * 完成任务事件
	 */
	String EVENTNAME_COMPLETE = "complete";

	/**
	 * 删除任务事件
	 */
	String EVENTNAME_DELETE = "delete";

	/**
	 * 所有任务事件
	 */
	String EVENTNAME_ALL_EVENTS = "all";

	/**
	 * 任务创建后触发此事件
	 * 
	 * @param task
	 * @param execution
	 */
	public void taskCreated(Task task, Execution execution)
	{

	}

	/**
	 * 任务分配参与者触发此事件
	 * 
	 * @param task
	 * @param execution
	 */
	public void taskAssigned(Task task, Execution execution)
	{

	}

	/**
	 * 任务完成触发此事件
	 * 
	 * @param task
	 * @param execution
	 */
	public void taskCompleted(Task task, Execution execution)
	{

	}

	/**
	 * 任务删除后触发此事件
	 * 
	 * @param task
	 * @param execution
	 */
	public void taskDeleted(Task task, Execution execution)
	{

	}
}
