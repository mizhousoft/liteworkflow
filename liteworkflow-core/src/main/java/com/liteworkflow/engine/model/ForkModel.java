package com.liteworkflow.engine.model;

import com.liteworkflow.engine.impl.Execution;

/**
 * 分支定义fork元素
 * 
 * @author yuqs
 * @since 1.0
 */
public class ForkModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2030281774771653617L;

	protected void exec(Execution execution)
	{
		runOutTransition(execution);
	}
}
