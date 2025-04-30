package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import org.springframework.util.Assert;
import org.w3c.dom.Element;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.BpmnConstant;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.ElementParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.ElementParseUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.DomUtils;

/**
 * 顺序流解析器
 *
 * @version
 */
public class SequenceFlowParseHandler extends ElementParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "sequenceFlow".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FlowElement parse(Element element)
	{
		SequenceFlowModel sequenceFlow = new SequenceFlowModel();

		sequenceFlow.setId(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_ID));
		sequenceFlow.setName(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_NAME));
		sequenceFlow.setSourceRef(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_SOURCEREF));
		sequenceFlow.setTargetRef(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_TARGETREF));

		Element conditionElement = DomUtils.listFirstChildElement(element, BpmnConstant.ELEMENT_CONDITIONEXPRESSION);
		if (null != conditionElement)
		{
			String condition = ElementParseUtils.parseTextContent(conditionElement);
			sequenceFlow.setConditionExpression(condition);
		}

		validateFlowElement(sequenceFlow);

		return sequenceFlow;
	}

	/**
	 * 校验元素
	 * 
	 * @param sequenceFlow
	 */
	private void validateFlowElement(SequenceFlowModel sequenceFlow)
	{
		Assert.notNull(sequenceFlow.getSourceRef(), "SequenceFlow sourceRef is null.");
		Assert.notNull(sequenceFlow.getTargetRef(), "SequenceFlow targetRef is null.");
	}
}
