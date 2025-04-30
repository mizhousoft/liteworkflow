package com.mizhousoft.liteworkflow.engine.impl.bpmn.validator;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;

/**
 * StartEventValidator
 *
 * @version
 */
public class StartEventValidator implements FlowElementValidator
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(FlowElement flowElement)
	{
		StartEventModel startEvent = (StartEventModel) flowElement;

		List<SequenceFlowModel> outgoingFlows = startEvent.getOutgoingFlows();
		if (outgoingFlows.isEmpty())
		{
			throw new WorkFlowException("Outgoing flow does not exist, id is " + flowElement.getId());
		}

		List<SequenceFlowModel> defaultFlows = outgoingFlows.stream().filter(flow -> flow.isDefaultSequenceFlow()).toList();
		if (defaultFlows.size() > 1)
		{
			throw new WorkFlowException("Default outgoing flow size is greater than 1, id is " + flowElement.getId());
		}
	}
}
