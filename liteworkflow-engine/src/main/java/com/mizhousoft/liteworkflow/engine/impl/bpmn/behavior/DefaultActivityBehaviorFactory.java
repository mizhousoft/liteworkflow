package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.ExclusiveGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.JoinGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.ParallelGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.constant.InstanceStatusEnum;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.ActivityBehaviorFactory;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;

/**
 * 流程执行器工厂类
 *
 * @version
 */
public class DefaultActivityBehaviorFactory implements ActivityBehaviorFactory
{
	/**
	 * 构建流程执行器
	 * 
	 * @param model
	 * @return
	 */
	public ActivityBehavior build(FlowElement model)
	{
		if (model instanceof StartEventModel startModel)
		{
			return new StartEventActivityBehavior(startModel);
		}
		else if (model instanceof ExclusiveGatewayModel gatewayModel)
		{
			return new ExclusiveGatewayActivityBehavior(gatewayModel);
		}
		else if (model instanceof EndEventModel endModel)
		{
			return new EndEventActivityBehavior(endModel, InstanceStatusEnum.COMPLETED);
		}
		else if (model instanceof ParallelGatewayModel gatewayModel)
		{
			return new ParallelGatewayActivityBehavior(gatewayModel);
		}
		else if (model instanceof JoinGatewayModel gatewayModel)
		{
			return new JoinGatewayActivityBehavior(gatewayModel);
		}
		else if (model instanceof UserTaskModel taskModel)
		{
			return new UserTaskActivityBehavior(taskModel);
		}
		else if (model instanceof SequenceFlowModel sequenceFlow)
		{
			return new SequenceFlowActivityBehavior(sequenceFlow);
		}

		throw new WorkFlowException("Model not support to build FlowExecutor.");
	}
}
