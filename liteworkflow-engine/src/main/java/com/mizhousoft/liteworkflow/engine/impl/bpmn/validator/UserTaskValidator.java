package com.mizhousoft.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.MultiInstanceLoopCharacteristics;
import com.mizhousoft.liteworkflow.bpmn.model.FlowOperation;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;

/**
 * UserTaskValidator
 *
 * @version
 */
public class UserTaskValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		UserTaskModel userTask = (UserTaskModel) flowElement;

		List<SequenceFlowModel> incomingFlows = userTask.getIncomingFlows();
		if (incomingFlows.isEmpty())
		{
			throw new WorkFlowException("Incoming flow does not exist, id is " + flowElement.getId());
		}

		List<SequenceFlowModel> outgoingFlows = userTask.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}

		MultiInstanceLoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
		if (null != loopCharacteristics)
		{
			if (StringUtils.isBlank(loopCharacteristics.getCollection()))
			{
				throw new WorkFlowException("MultiInstanceLoopCharacteristics collection is null, id is " + flowElement.getId());
			}
			else if (StringUtils.isBlank(loopCharacteristics.getElementVariable()))
			{
				throw new WorkFlowException("MultiInstanceLoopCharacteristics elementVariable is null, id is " + flowElement.getId());
			}
			else if (StringUtils.isBlank(loopCharacteristics.getCompletionCondition()))
			{
				throw new WorkFlowException("MultiInstanceLoopCharacteristics completionCondition is null, id is " + flowElement.getId());
			}
		}

		List<FlowOperation> operations = userTask.getOperations();
		for (FlowOperation operation : operations)
		{
			if (StringUtils.isBlank(operation.getId()))
			{
				throw new WorkFlowException("Operation id is null, task id is " + flowElement.getId());
			}

			if (StringUtils.isBlank(operation.getLabel()))
			{
				throw new WorkFlowException("Operation label is null, task id is " + flowElement.getId());
			}

			List<FlowElement> backToNodes = operation.getBackToNodes();
			for (FlowElement backToNode : backToNodes)
			{
				FlowElement backElement = getBackFlowElement(backToNode.getId(), userTask);
				if (null == backElement)
				{
					throw new WorkFlowException(
					        "Operation backToNodeId is null, task id is " + flowElement.getId() + ", toNodeId is " + backToNode.getId());
				}

				backToNode.setName(backElement.getName());
			}
		}
	}

	private FlowElement getBackFlowElement(String backNodeId, FlowNode flowNode)
	{
		List<SequenceFlowModel> inFlows = flowNode.getIncomingFlows();
		for (SequenceFlowModel inFlow : inFlows)
		{
			FlowNode sourceNode = inFlow.getSourceNode();
			if (sourceNode instanceof UserTaskModel && sourceNode.getId().equals(backNodeId))
			{
				return sourceNode;
			}
			else
			{
				FlowElement backElement = getBackFlowElement(backNodeId, sourceNode);
				if (null != backElement)
				{
					return backElement;
				}
			}
		}

		return null;
	}
}
