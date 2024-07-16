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
			String message = "Source node not found, id is " + flowElement.getId() + ", sourceRef is " + sequenceFlow.getSourceRef()
			        + ", targetRef is " + sequenceFlow.getTargetRef();

			throw new WorkFlowException(message);
		}
		else if (null == sequenceFlow.getTargetNode())
		{
			String message = "Target node not found, id is " + flowElement.getId() + ", sourceRef is " + sequenceFlow.getSourceRef()
			        + ", targetRef is " + sequenceFlow.getTargetRef();

			throw new WorkFlowException(message);
		}
	}
}
