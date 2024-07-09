package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.ExclusiveGatewayModel;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.model.StartEventModel;
import com.liteworkflow.engine.model.UserTaskModel;

/**
 * 流程执行器工厂类
 *
 * @version
 */
public class FlowExecutorFactory
{
	/**
	 * 构建流程执行器
	 * 
	 * @param model
	 * @return
	 */
	public static FlowExecutor build(FlowElement model)
	{
		if (model instanceof StartEventModel)
		{
			return new StartExecutor();
		}
		else if (model instanceof ExclusiveGatewayModel)
		{
			return new ExclusiveGatewayExecutor();
		}
		else if (model instanceof EndEventModel)
		{
			return new EndExecutor();
		}
		else if (model instanceof ForkGatewayModel)
		{
			return new ForkGatewayExecutor();
		}
		else if (model instanceof JoinGatewayModel)
		{
			return new JoinGatewayExecutor();
		}
		else if (model instanceof UserTaskModel)
		{
			return new UserTaskExecutor();
		}
		else if (model instanceof SequenceFlowModel)
		{
			return new SequenceFlowExecutor();
		}

		throw new WorkFlowException("Model not support to build FlowExecutor.");
	}
}
