package com.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.model.UserTaskModel;

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
	}

}
