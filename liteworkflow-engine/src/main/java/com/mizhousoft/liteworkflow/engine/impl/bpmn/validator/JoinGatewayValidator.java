package com.mizhousoft.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.ActivityModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.JoinGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;

/**
 * JoinGatewayValidator
 *
 * @version
 */
public class JoinGatewayValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		JoinGatewayModel joinGateway = (JoinGatewayModel) flowElement;

		List<SequenceFlowModel> incomingFlows = joinGateway.getIncomingFlows();
		if (incomingFlows.isEmpty())
		{
			throw new WorkFlowException("Incoming flow does not exist, id is " + flowElement.getId());
		}
		for (SequenceFlowModel incomingFlow : incomingFlows)
		{
			FlowNode sourceNode = incomingFlow.getSourceNode();
			if (sourceNode instanceof ActivityModel)
			{
				continue;
			}
			else if (sourceNode instanceof JoinGatewayModel)
			{
				continue;
			}
			else
			{
				throw new WorkFlowException("Incoming flow is invalid, id is " + flowElement.getId());
			}
		}

		List<SequenceFlowModel> outgoingFlows = joinGateway.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}
	}

}
