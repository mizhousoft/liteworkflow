package com.mizhousoft.liteworkflow.engine.impl.delegate;

import com.mizhousoft.liteworkflow.engine.impl.Execution;

/**
 * 活动行为
 * 
 * @version
 */
public interface ActivityBehavior
{
	/**
	 * 执行流程
	 * 
	 * @param execution
	 * @return
	 */
	boolean execute(Execution execution);
}
