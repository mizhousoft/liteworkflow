package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Executor;
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
 * TODO
 *
 * @version
 */
public class ExecutorBuilder
{
	public static Executor build(BaseModel model)
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

		return null;
	}
}
