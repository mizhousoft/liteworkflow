package com.mizhousoft.liteworkflow.engine.util;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.ActivityModel;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.SelectTargetType;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;

/**
 * 工具类
 *
 */
public abstract class FlowNodeUtils
{
	/**
	 * 获取受理人变量名称
	 * 
	 * @param userTask
	 * @return
	 */
	public static String getAssigneeVarName(UserTaskModel userTask)
	{
		String assignee = userTask.getAssignee();
		if (null == assignee)
		{
			return null;
		}

		if (assignee.contains("#"))
		{
			return assignee.replaceFirst("#", "");
		}

		return null;
	}

	/**
	 * 是否流程第一个发起人任务
	 * 
	 * @param bpmnModel
	 * @param flowNodeId
	 * @return
	 */
	public static boolean isFlowFirstInitiatorUserTask(BpmnModel bpmnModel, String flowNodeId)
	{
		SequenceFlowModel sequenceFlow = bpmnModel.getStartModel()
		        .getOutgoingFlows()
		        .stream()
		        .filter(o -> o.getTargetRef().equals(flowNodeId))
		        .findFirst()
		        .orElse(null);
		if (null == sequenceFlow)
		{
			return false;
		}

		FlowNode flowNode = sequenceFlow.getTargetNode();
		if (flowNode instanceof UserTaskModel userTask)
		{
			return SelectTargetType.TARGET_INITIATOR.equals(userTask.getTargetType());
		}

		return false;
	}

	/**
	 * 是否流程最后的活动节点
	 * 
	 * @param bpmnModel
	 * @param nodeId
	 * @return
	 */
	public static boolean isFlowFinalActivityNode(BpmnModel bpmnModel, String nodeId)
	{
		FlowNode flowNode = bpmnModel.getFlowNodeModel(nodeId);
		if (null != flowNode)
		{
			List<SequenceFlowModel> outFlows = flowNode.getOutgoingFlows();
			if (outFlows.size() == 1)
			{
				FlowNode targetNode = outFlows.get(0).getTargetNode();
				if (targetNode instanceof EndEventModel)
				{
					return true;
				}
				else if (targetNode instanceof ActivityModel)
				{
					return false;
				}
				else
				{
					return isFlowFinalActivityNode(bpmnModel, targetNode.getId());
				}
			}
		}

		return false;
	}
}
