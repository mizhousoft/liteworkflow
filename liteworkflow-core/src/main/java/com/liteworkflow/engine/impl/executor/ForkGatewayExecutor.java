package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

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
	protected boolean doExecute(Execution execution, FlowNode nodeModel)
	{
		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			if (canSequenceFlowExecute(execution, outgoingFlow))
			{
				FlowExecutor executor = FlowExecutorFactory.build(outgoingFlow);
				executor.execute(execution, outgoingFlow);
			}
		}

		return true;
	}
}
