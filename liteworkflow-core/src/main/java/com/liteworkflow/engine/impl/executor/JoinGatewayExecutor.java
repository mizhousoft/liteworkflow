package com.liteworkflow.engine.impl.executor;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.ActivityModel;
import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * TODO
 *
 * @version
 */
public class JoinGatewayExecutor extends NodeFlowExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, FlowNode nodeModel)
	{
		JoinGatewayModel joinModel = (JoinGatewayModel) nodeModel;

		boolean isMerged = isBranchMerged(execution, joinModel);
		execution.setMerged(isMerged);

		if (execution.isMerged())
		{
			runOutTransition(execution, nodeModel);
		}
	}

	/**
	 * 查询当前流程实例的无法参与合并的node列表
	 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
	 */
	public boolean isBranchMerged(Execution execution, JoinGatewayModel joinModel)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskService taskService = engineConfiguration.getTaskService();

		ProcessInstance instance = execution.getProcessInstance();
		BpmnModel bpmnModel = execution.getBpmnModel();
		String[] activeNodes = findActiveNodes(joinModel);
		boolean isTaskMerged = false;

		if (bpmnModel.containsNodeNames(UserTaskModel.class, activeNodes))
		{
			List<Task> tasks = taskService.queryByInstanceId(instance.getId());
			tasks = tasks.stream()
			        .filter(task -> !task.getId().equals(execution.getTask().getId()))
			        .filter(task -> ArrayUtils.contains(activeNodes, task.getTaskDefinitionId()))
			        .collect(Collectors.toList());
			if (tasks == null || tasks.isEmpty())
			{
				// 如果所有task都已完成，则表示可合并
				isTaskMerged = true;
			}
		}

		boolean merged = isTaskMerged;

		return merged;
	}

	/**
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 * @param node
	 * @param buffer
	 */
	private void findForkTaskNames(FlowNode node, StringBuilder buffer)
	{
		if (node instanceof ForkGatewayModel)
		{
			return;
		}

		List<TransitionModel> inputs = node.getInputs();
		for (TransitionModel tm : inputs)
		{
			if (tm.getSource() instanceof ActivityModel)
			{
				buffer.append(tm.getSource().getId()).append(",");
			}
			findForkTaskNames(tm.getSource(), buffer);
		}
	}

	/**
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 */
	protected String[] findActiveNodes(JoinGatewayModel joinModel)
	{
		StringBuilder buffer = new StringBuilder(20);
		findForkTaskNames(joinModel, buffer);
		String[] taskNames = buffer.toString().split(",");
		return taskNames;
	}
}
