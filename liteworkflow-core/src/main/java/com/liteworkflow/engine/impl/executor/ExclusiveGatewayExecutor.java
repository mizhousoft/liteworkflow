package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * 排除网关执行器
 *
 * @version
 */
public class ExclusiveGatewayExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean doExecute(Execution execution, FlowNode nodeModel)
	{
		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();

		List<SequenceFlowModel> conditionFlows = outgoingFlows.stream().filter(of -> null != of.getConditionExpression()).toList();
		for (SequenceFlowModel conditionFlow : conditionFlows)
		{
			FlowExecutor flowExecutor = FlowExecutorFactory.build(conditionFlow);
			boolean succeed = flowExecutor.execute(execution, conditionFlow);
			if (succeed)
			{
				return true;
			}
		}

		SequenceFlowModel defaultFlow = outgoingFlows.stream().filter(of -> null == of.getConditionExpression()).findFirst().orElse(null);
		if (null != defaultFlow)
		{
			FlowExecutor flowExecutor = FlowExecutorFactory.build(defaultFlow);
			boolean succeed = flowExecutor.execute(execution, defaultFlow);
			if (succeed)
			{
				return true;
			}
		}

		throw new WorkFlowException("No matching sequential flow is executed.");
	}
}
