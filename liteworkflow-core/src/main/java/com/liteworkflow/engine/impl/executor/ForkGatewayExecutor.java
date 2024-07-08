package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.FlowNode;

/**
 * Fork执行器
 *
 * @version
 */
public class ForkGatewayExecutor extends NodeFlowExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, FlowNode nodeModel)
	{
		runOutTransition(execution, nodeModel);
	}
}
