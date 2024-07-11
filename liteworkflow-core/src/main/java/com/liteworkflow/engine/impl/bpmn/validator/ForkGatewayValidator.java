package com.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * ForkGatewayValidator
 *
 * @version
 */
public class ForkGatewayValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		ForkGatewayModel forkGateway = (ForkGatewayModel) flowElement;

		List<SequenceFlowModel> incomingFlows = forkGateway.getIncomingFlows();
		if (incomingFlows.isEmpty())
		{
			throw new WorkFlowException("Incoming flow does not exist, id is " + flowElement.getId());
		}

		List<SequenceFlowModel> outgoingFlows = forkGateway.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}
	}

}
