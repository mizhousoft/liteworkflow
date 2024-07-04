package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.DecisionModel;
import com.liteworkflow.engine.model.EndModel;
import com.liteworkflow.engine.model.ForkModel;
import com.liteworkflow.engine.model.JoinModel;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TransitionModel;

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
	public static FlowExecutor build(BaseModel model)
	{
		if (model instanceof StartModel)
		{
			return new StartExecutor();
		}
		else if (model instanceof DecisionModel)
		{
			return new DecisionExecutor();
		}
		else if (model instanceof EndModel)
		{
			return new EndExecutor();
		}
		else if (model instanceof ForkModel)
		{
			return new ForkExecutor();
		}
		else if (model instanceof JoinModel)
		{
			return new JoinExecutor();
		}
		else if (model instanceof SubProcessModel)
		{
			return new SubProcessExecutor();
		}
		else if (model instanceof TaskModel)
		{
			return new TaskExecutor();
		}
		else if (model instanceof TransitionModel)
		{
			return new TransitionExecutor();
		}

		throw new WorkFlowException("Model not support to build FlowExecutor.");
	}
}
