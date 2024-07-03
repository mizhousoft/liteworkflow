package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.NodeModel;

/**
 * TODO
 *
 * @version
 */
public class ForkExecutor extends NodeFlowExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		runOutTransition(execution, nodeModel);
	}
}
