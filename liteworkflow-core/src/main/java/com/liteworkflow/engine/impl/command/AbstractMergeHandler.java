package com.liteworkflow.engine.impl.command;

import java.util.List;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.IHandler;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.ProcessInstPageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

/**
 * 合并处理的抽象处理器
 * 需要子类提供查询无法合并的task集合的参数map
 * 
 * @author yuqs
 * @since 1.0
 */
public abstract class AbstractMergeHandler implements IHandler
{
	public void handle(Execution execution)
	{
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		ProcessInstanceService processInstanceService = execution.getEngineConfiguration().getProcessInstanceService();
		TaskService taskService = execution.getEngineConfiguration().getTaskService();

		ProcessInstance instance = execution.getInstance();
		ProcessModel model = execution.getModel();
		String[] activeNodes = findActiveNodes();
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
		execution.setMerged(isSubProcessMerged && isTaskMerged);
	}

	/**
	 * 子类需要提供如何查询未合并任务的参数map
	 * 
	 * @return
	 */
	protected abstract String[] findActiveNodes();
}
