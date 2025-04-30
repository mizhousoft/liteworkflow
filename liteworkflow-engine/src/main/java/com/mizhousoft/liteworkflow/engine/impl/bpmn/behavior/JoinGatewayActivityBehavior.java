package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mizhousoft.liteworkflow.bpmn.model.ActivityModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.JoinGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.ParallelGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;

/**
 * 合并网关执行器
 *
 * @version
 */
public class JoinGatewayActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 模型
	 */
	private JoinGatewayModel joinModel;

	/**
	 * 构造函数
	 *
	 * @param joinModel
	 */
	public JoinGatewayActivityBehavior(JoinGatewayModel joinModel)
	{
		super();
		this.joinModel = joinModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		boolean isMerged = isBranchMerged(execution, joinModel);
		if (isMerged)
		{
			List<SequenceFlowModel> outgoingFlows = joinModel.getOutgoingFlows();
			for (SequenceFlowModel outgoingFlow : outgoingFlows)
			{
				ActivityBehavior executor = execution.getEngineConfiguration().getActivityBehaviorFactory().build(outgoingFlow);
				executor.execute(execution);
			}
		}

		return true;
	}

	/**
	 * 
	 * 
	 * @param execution
	 * @param joinModel
	 * @return
	 */
	public boolean isBranchMerged(Execution execution, JoinGatewayModel joinModel)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		ProcessInstanceEntity instance = execution.getProcessInstance();
		Set<String> parallelTaskIds = findParallelTaskIds(joinModel);
		boolean merged = false;

		List<TaskEntity> tasks = taskEntityService.queryByInstanceId(instance.getId());
		tasks = tasks.stream()
		        .filter(task -> task.getId() != execution.getTask().getId())
		        .filter(task -> parallelTaskIds.contains(task.getTaskDefinitionKey()))
		        .collect(Collectors.toList());
		if (tasks.isEmpty())
		{
			merged = true;
		}

		return merged;
	}

	/**
	 * 获取并行任务ID列表
	 * 
	 * @param flowNode
	 * @return
	 */
	private Set<String> findParallelTaskIds(FlowNode flowNode)
	{
		if (flowNode instanceof ParallelGatewayModel)
		{
			return Collections.emptySet();
		}

		Set<String> nodeIds = new HashSet<>(5);

		List<SequenceFlowModel> incomingFlows = flowNode.getIncomingFlows();
		for (SequenceFlowModel incomingFlow : incomingFlows)
		{
			FlowNode sourceNode = incomingFlow.getSourceNode();

			if (sourceNode instanceof ActivityModel)
			{
				nodeIds.add(sourceNode.getId());
			}
			else if (sourceNode instanceof JoinGatewayModel)
			{
				Set<String> ids = findParallelTaskIds(incomingFlow.getSourceNode());
				nodeIds.addAll(ids);
			}
		}

		return nodeIds;
	}
}
