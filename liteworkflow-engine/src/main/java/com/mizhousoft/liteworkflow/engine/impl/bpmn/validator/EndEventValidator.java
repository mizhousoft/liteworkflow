package com.mizhousoft.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;

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
