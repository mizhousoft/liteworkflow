package com.mizhousoft.liteworkflow.engine.delegate;

/**
 * 执行器事件监听器
 *
 * @version
 */
public abstract class ExecutionListener
{
	/**
	 * 开启事件名称
	 */
	public static final String EVENTNAME_START = "start";

	/**
	 * 结束事件名称
	 */
	public static final String EVENTNAME_END = "end";

	/**
	 * 取消事件名称
	 */
	public static final String EVENTNAME_CANCELLED = "canceled";

	/**
	 * 流程实例开启触发此事件
	 * 
	 * @param execution
	 */
	public void processStarted(DelegateExecution execution)
	{

	}

	/**
	 * 流程实例完成触发此事件
	 * 
	 * @param execution
	 */
	public void processCompleted(DelegateExecution execution)
	{

	}

	/**
	 * 流程实例取消触发此事件
	 * 
	 * @param execution
	 */
	public void processCancelled(DelegateExecution execution)
	{

	}
}
