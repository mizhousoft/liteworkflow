package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.ParallelGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;

/**
 * 并行网关执行器
 *
 * @version
 */
public class ParallelGatewayActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 模型
	 */
	private ParallelGatewayModel gatewayModel;

	/**
	 * 构造函数
	 *
	 * @param gatewayModel
	 */
	public ParallelGatewayActivityBehavior(ParallelGatewayModel gatewayModel)
	{
		super();
		this.gatewayModel = gatewayModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		List<SequenceFlowModel> outgoingFlows = gatewayModel.getOutgoingFlows();
		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			ActivityBehavior executor = execution.getEngineConfiguration().getActivityBehaviorFactory().build(outgoingFlow);
			executor.execute(execution);
		}

		return true;
	}
}
