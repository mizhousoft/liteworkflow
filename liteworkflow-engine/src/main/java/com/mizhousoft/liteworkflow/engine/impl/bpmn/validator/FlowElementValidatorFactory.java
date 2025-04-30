package com.mizhousoft.liteworkflow.engine.impl.bpmn.validator;

import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.ExclusiveGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.JoinGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.ParallelGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;

/**
 * 工厂类
 *
 * @version
 */
public abstract class FlowElementValidatorFactory
{
	/**
	 * 构建校验器
	 * 
	 * @param flowElement
	 * @return
	 */
	public static FlowElementValidator build(FlowElement flowElement)
	{
		if (flowElement instanceof StartEventModel)
		{
			return new StartEventValidator();
		}
		else if (flowElement instanceof EndEventModel)
		{
			return new EndEventValidator();
		}
		else if (flowElement instanceof UserTaskModel)
		{
			return new UserTaskValidator();
		}
		else if (flowElement instanceof ParallelGatewayModel)
		{
			return new ParallelGatewayValidator();
		}
		else if (flowElement instanceof JoinGatewayModel)
		{
			return new JoinGatewayValidator();
		}
		else if (flowElement instanceof ExclusiveGatewayModel)
		{
			return new ExclusiveGatewayValidator();
		}
		else if (flowElement instanceof SequenceFlowModel)
		{
			return new SequenceFlowValidator();
		}

		return new DefaultFlowElementValidator();
	}
}
