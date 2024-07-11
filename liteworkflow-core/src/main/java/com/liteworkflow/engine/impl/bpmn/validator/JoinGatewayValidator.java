package com.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.model.SequenceFlowModel;

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

		List<SequenceFlowModel> outgoingFlows = joinGateway.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}
	}

}
