package com.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * EndEventValidator
 *
 * @version
 */
public class EndEventValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		EndEventModel endEvent = (EndEventModel) flowElement;

		List<SequenceFlowModel> sequenceFlows = endEvent.getIncomingFlows();
		if (sequenceFlows.isEmpty())
		{
			throw new WorkFlowException("Incoming flow does not exist, id is " + flowElement.getId());
		}
	}

}
