package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.ExclusiveGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.ActivityBehaviorFactory;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;

/**
 * 排除网关执行器
 *
 * @version
 */
public class ExclusiveGatewayActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 网关模型
	 */
	private ExclusiveGatewayModel gatewayModel;

	/**
	 * 构造函数
	 *
	 * @param gatewayModel
	 */
	public ExclusiveGatewayActivityBehavior(ExclusiveGatewayModel gatewayModel)
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
		ActivityBehaviorFactory activityBehaviorFactory = execution.getEngineConfiguration().getActivityBehaviorFactory();

		List<SequenceFlowModel> outgoingFlows = gatewayModel.getOutgoingFlows();

		List<SequenceFlowModel> conditionFlows = outgoingFlows.stream().filter(of -> !of.isDefaultSequenceFlow()).toList();
		for (SequenceFlowModel conditionFlow : conditionFlows)
		{
			ActivityBehavior flowExecutor = activityBehaviorFactory.build(conditionFlow);
			boolean succeed = flowExecutor.execute(execution);
			if (succeed)
			{
				return true;
			}
		}

		SequenceFlowModel defaultFlow = outgoingFlows.stream().filter(of -> of.isDefaultSequenceFlow()).findFirst().orElse(null);
		if (null != defaultFlow)
		{
			ActivityBehavior flowExecutor = activityBehaviorFactory.build(defaultFlow);
			boolean succeed = flowExecutor.execute(execution);
			if (succeed)
			{
				return true;
			}
		}

		throw new WorkFlowException("No matching sequential flow is executed.");
	}
}
