package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.ActivityModel;
import com.liteworkflow.engine.model.ForkModel;
import com.liteworkflow.engine.model.JoinModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;

/**
 * TODO
 *
 * @version
 */
public class JoinExecutor extends NodeExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		JoinModel joinModel = (JoinModel) nodeModel;

		boolean isMerged = isBranchMerged(execution, joinModel);
		execution.setMerged(isMerged);

		if (execution.isMerged())
		{
			runOutTransition(execution, nodeModel);
		}
	}

	public boolean isBranchMerged(Execution execution, JoinModel joinModel)
	{
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		TaskService taskService = execution.getEngineConfiguration().getTaskService();
		ProcessInstanceEntityService processInstanceEntityService = execution.getEngineConfiguration().getProcessInstanceEntityService();

		ProcessInstance instance = execution.getProcessInstance();
		ProcessModel model = execution.getProcessModel();
		String[] activeNodes = findActiveNodes(joinModel);
		boolean isSubProcessMerged = false;
		boolean isTaskMerged = false;

		if (model.containsNodeNames(SubProcessModel.class, activeNodes))
		{
			List<ProcessInstance> instances = processInstanceEntityService.queryByParentId(instance.getId());
			instances = instances.stream().filter(i -> !i.getId().equals(execution.getChildInstanceId())).toList();

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
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 * @param node
	 * @param buffer
	 */
	private void findForkTaskNames(NodeModel node, StringBuilder buffer)
	{
		if (node instanceof ForkModel)
		{
			return;
		}

		List<TransitionModel> inputs = node.getInputs();
		for (TransitionModel tm : inputs)
		{
			if (tm.getSource() instanceof ActivityModel)
			{
				buffer.append(tm.getSource().getName()).append(",");
			}
			findForkTaskNames(tm.getSource(), buffer);
		}
	}

	/**
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 */
	protected String[] findActiveNodes(JoinModel joinModel)
	{
		StringBuilder buffer = new StringBuilder(20);
		findForkTaskNames(joinModel, buffer);
		String[] taskNames = buffer.toString().split(",");
		return taskNames;
	}
}
