package com.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.ExclusiveGatewayModel;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * ExclusiveGatewayValidator
 *
 * @version
 */
public class ExclusiveGatewayValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		ExclusiveGatewayModel exclusiveGateway = (ExclusiveGatewayModel) flowElement;

		List<SequenceFlowModel> incomingFlows = exclusiveGateway.getIncomingFlows();
		if (incomingFlows.isEmpty())
		{
			throw new WorkFlowException("Incoming flow does not exist, id is " + flowElement.getId());
		}

		List<SequenceFlowModel> outgoingFlows = exclusiveGateway.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}
		List<SequenceFlowModel> defaultFlows = outgoingFlows.stream().filter(flow -> null == flow.getConditionExpression()).toList();
		if (defaultFlows.size() > 1)
		{
			throw new WorkFlowException("Default outgoing flow size is greater than 1, id is " + flowElement.getId());
		}
	}

}
