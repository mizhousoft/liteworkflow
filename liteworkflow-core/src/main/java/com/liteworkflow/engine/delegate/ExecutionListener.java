package com.liteworkflow.engine.delegate;

import com.liteworkflow.engine.impl.Execution;

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
	 * 转移事件名称
	 */
	public static final String EVENTNAME_TAKE = "take";

	/**
	 * 流程实例创建触发此事件
	 * 
	 * @param execution
	 */
	protected void processCreated(Execution execution)
	{

	}

	/**
	 * 流程实例开启触发此事件
	 * 
	 * @param execution
	 */
	protected void processStarted(Execution execution)
	{

	}

	/**
	 * 流程实例完成触发此事件
	 * 
	 * @param execution
	 */
	protected void processCompleted(Execution execution)
	{

	}

	/**
	 * 流程实例取消触发此事件
	 * 
	 * @param execution
	 */
	protected void processCancelled(Execution execution)
	{

	}
}
