package com.mizhousoft.liteworkflow.engine.delegate;

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
	public static final String EVENTNAME_CREATE = "create";

	/**
	 * 分配参与者任务事件
	 */
	public static final String EVENTNAME_ASSIGNMENT = "assignment";

	/**
	 * 完成任务事件
	 */
	public static final String EVENTNAME_COMPLETE = "complete";

	/**
	 * 删除任务事件
	 */
	public static final String EVENTNAME_DELETE = "delete";

	/**
	 * 所有任务事件
	 */
	public static final String EVENTNAME_ALL_EVENTS = "all";

	/**
	 * 任务创建后触发此事件
	 * 
	 * @param task
	 */
	public void taskCreated(DelegateTask task)
	{

	}

	/**
	 * 任务分配参与者触发此事件
	 * 
	 * @param task
	 */
	public void taskAssigned(DelegateTask task)
	{

	}

	/**
	 * 任务完成触发此事件
	 * 
	 * @param task
	 */
	public void taskCompleted(DelegateTask task)
	{

	}

	/**
	 * 任务删除后触发此事件
	 * 
	 * @param task
	 */
	public void taskDeleted(DelegateTask task)
	{

	}
}
