package com.liteworkflow.engine.impl.bpmn.validator;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * SequenceFlowValidator
 *
 * @version
 */
public class SequenceFlowValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		SequenceFlowModel sequenceFlow = (SequenceFlowModel) flowElement;

		if (null == sequenceFlow.getSourceNode())
		{
			throw new WorkFlowException("Source node does not exist, id is " + flowElement.getId());
		}
		else if (null == sequenceFlow.getTargetNode())
		{
			throw new WorkFlowException("Target node does not exist, id is " + flowElement.getId());
		}
	}
}
