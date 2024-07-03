package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.NodeModel;

/**
 * 开始事件流程执行器
 *
 * @version
 */
public class StartExecutor extends NodeFlowExecutor
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
