package com.liteworkflow.engine.impl.bpmn.validator;

import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.ExclusiveGatewayModel;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.model.StartEventModel;
import com.liteworkflow.engine.model.UserTaskModel;

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
		else if (flowElement instanceof ForkGatewayModel)
		{
			return new ForkGatewayValidator();
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
