package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.command.MergeActorHandler;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TaskModel;

/**
 * TODO
 *
 * @version
 */
public class TaskExecutor extends NodeExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		TaskModel taskModel = (TaskModel) nodeModel;
		String performType = taskModel.getPerformType();

		if (performType == null || performType.equalsIgnoreCase(TaskModel.PERFORMTYPE_ANY))
		{
			/**
			 * any方式，直接执行输出变迁
			 */
			runOutTransition(execution, taskModel);
		}
		else
		{
			/**
			 * all方式，需要判断是否已全部合并
			 * 由于all方式分配任务，是每人一个任务
			 * 那么此时需要判断之前分配的所有任务都执行完成后，才可执行下一步，否则不处理
			 */
			new MergeActorHandler(taskModel.getName()).handle(execution);

			if (execution.isMerged())
				runOutTransition(execution, taskModel);
		}
	}
}
