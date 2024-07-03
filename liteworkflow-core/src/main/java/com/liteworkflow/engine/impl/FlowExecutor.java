package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.model.BaseModel;

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
	 */
	public void execute(Execution execution, BaseModel model);
}
