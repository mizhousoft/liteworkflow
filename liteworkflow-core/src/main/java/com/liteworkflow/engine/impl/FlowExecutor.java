package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.model.FlowElement;

/**
 * 流程执行器
 * 
 * @version
 */
public interface FlowExecutor
{
	/**
	 * 执行流程
	 * 
	 * @param execution
	 * @param model
	 * @return
	 */
	boolean execute(Execution execution, FlowElement model);
}
