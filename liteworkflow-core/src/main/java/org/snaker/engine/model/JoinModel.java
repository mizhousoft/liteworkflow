package org.snaker.engine.model;

import org.snaker.engine.core.Execution;
import org.snaker.engine.handlers.impl.MergeBranchHandler;

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

	public void exec(Execution execution)
	{
		fire(new MergeBranchHandler(this), execution);
		if (execution.isMerged())
			runOutTransition(execution);
	}
}
