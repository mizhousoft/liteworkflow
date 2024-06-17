package com.liteworkflow.engine.model;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.command.MergeBranchHandler;

/**
 * 合并定义join元素
 * 
 * @author yuqs
 * @since 1.0
 */
public class JoinModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5296621319088076775L;

	public void doExecute(Execution execution)
	{
		fire(new MergeBranchHandler(this), execution);
		if (execution.isMerged())
			runOutTransition(execution);
	}
}
