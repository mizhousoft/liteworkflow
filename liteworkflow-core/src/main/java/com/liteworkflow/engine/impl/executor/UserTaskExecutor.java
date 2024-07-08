package com.liteworkflow.engine.impl.executor;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * TODO
 *
 * @version
 */
public class UserTaskExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, FlowNode nodeModel)
	{
		UserTaskModel taskModel = (UserTaskModel) nodeModel;
		String performType = taskModel.getPerformType();

		if (performType == null || performType.equalsIgnoreCase(UserTaskModel.PERFORMTYPE_ANY))
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

	/**
	 * 查询当前流程实例的无法参与合并的node列表
	 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
	 */
	public boolean isMerged(Execution execution, UserTaskModel taskModel)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskService taskService = engineConfiguration.getTaskService();

		ProcessInstance instance = execution.getProcessInstance();
		BpmnModel bpmnModel = execution.getBpmnModel();
		String[] activeNodes = findActiveNodes(taskModel);
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
	 * actor all方式，查询参数为：taskName
	 * 
	 */
	protected String[] findActiveNodes(UserTaskModel taskModel)
	{
		return new String[] { taskModel.getId() };
	}
}
