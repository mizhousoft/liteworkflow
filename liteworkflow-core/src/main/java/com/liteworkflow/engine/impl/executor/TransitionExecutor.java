package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.impl.command.CreateTaskHandler;
import com.liteworkflow.engine.impl.command.StartSubProcessHandler;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TransitionModel;

/**
 * TODO
 *
 * @version
 */
public class TransitionExecutor implements Executor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Execution execution, BaseModel model)
	{
		TransitionModel transitionModel = (TransitionModel) model;

		boolean enabled = transitionModel.isEnabled();
		if (!enabled)
		{
			return;
		}

		NodeModel target = transitionModel.getTarget();
		if (target instanceof TaskModel)
		{
			new CreateTaskHandler((TaskModel) target).handle(execution);
		}
		else if (target instanceof SubProcessModel)
		{
			new StartSubProcessHandler((SubProcessModel) target).handle(execution);
		}
		else
		{
			// 如果目标节点模型为其它控制类型，则继续由目标节点执行
			Executor executor = ExecutorBuilder.build(target);
			executor.execute(execution, target);
		}
	}
}
