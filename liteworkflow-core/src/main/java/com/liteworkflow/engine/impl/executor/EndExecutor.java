package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.command.EndProcessHandler;
import com.liteworkflow.engine.model.NodeModel;

/**
 * TODO
 *
 * @version
 */
public class EndExecutor extends NodeExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		new EndProcessHandler().handle(execution);
	}
}
