package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.ProcessInstPageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

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
			boolean isMerged = isMerged(execution, taskModel);
			execution.setMerged(isMerged);

			if (execution.isMerged())
			{
				runOutTransition(execution, taskModel);
			}
		}
	}

	public boolean isMerged(Execution execution, TaskModel taskModel)
	{
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		ProcessInstanceService processInstanceService = execution.getEngineConfiguration().getProcessInstanceService();
		TaskService taskService = execution.getEngineConfiguration().getTaskService();

		ProcessInstance instance = execution.getInstance();
		ProcessModel model = execution.getModel();
		String[] activeNodes = findActiveNodes(taskModel);
		boolean isSubProcessMerged = false;
		boolean isTaskMerged = false;

		if (model.containsNodeNames(SubProcessModel.class, activeNodes))
		{
			ProcessInstPageRequest request = new ProcessInstPageRequest();
			request.setParentId(instance.getId());
			request.setExcludedIds(new String[] { execution.getChildOrderId() });
			List<ProcessInstance> instances = processInstanceService.getActiveInstances(request);
			// 如果所有子流程都已完成，则表示可合并
			if (instances == null || instances.isEmpty())
			{
				isSubProcessMerged = true;
			}
		}
		else
		{
			isSubProcessMerged = true;
		}
		if (isSubProcessMerged && model.containsNodeNames(TaskModel.class, activeNodes))
		{
			TaskPageRequest request = new TaskPageRequest();
			request.setInstanceId(instance.getId());
			request.setExcludedIds(new String[] { execution.getTask().getId() });
			request.setNames(activeNodes);

			List<Task> tasks = taskService.getActiveTasks(request);
			if (tasks == null || tasks.isEmpty())
			{
				// 如果所有task都已完成，则表示可合并
				isTaskMerged = true;
			}
		}

		boolean merged = isSubProcessMerged && isTaskMerged;

		return merged;
	}

	/**
	 * actor all方式，查询参数为：taskName
	 * 
	 */
	protected String[] findActiveNodes(TaskModel taskModel)
	{
		return new String[] { taskModel.getName() };
	}
}
