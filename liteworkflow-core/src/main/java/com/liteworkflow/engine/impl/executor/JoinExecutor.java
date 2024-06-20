package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.command.MergeBranchHandler;
import com.liteworkflow.engine.model.JoinModel;
import com.liteworkflow.engine.model.NodeModel;

/**
 * TODO
 *
 * @version
 */
public class JoinExecutor extends NodeExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		new MergeBranchHandler((JoinModel) nodeModel).handle(execution);

		if (execution.isMerged())
			runOutTransition(execution, nodeModel);
	}
}
