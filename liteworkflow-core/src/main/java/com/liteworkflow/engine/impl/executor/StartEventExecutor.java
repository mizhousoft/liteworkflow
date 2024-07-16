package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * 开始事件流程执行器
 *
 * @version
 */
public class StartEventExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean doExecute(Execution execution, FlowNode nodeModel)
	{
		boolean succeed = false;

		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			FlowExecutor flowExecutor = FlowExecutorFactory.build(outgoingFlow);
			boolean result = flowExecutor.execute(execution, outgoingFlow);

			if (result)
			{
				succeed = result;
			}
		}

		if (!succeed)
		{
			throw new WorkFlowException("No matching sequential flow is executed.");
		}

		return succeed;
	}
}
