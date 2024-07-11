package com.liteworkflow.engine.impl.bpmn.parser.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

import com.liteworkflow.engine.impl.bpmn.parser.ElementParseHandler;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.util.DomUtils;

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

		sequenceFlow.setId(getAttribute(element, ATTR_ID));
		sequenceFlow.setName(getAttribute(element, ATTR_NAME));
		sequenceFlow.setSourceRef(getAttribute(element, ATTR_SOURCEREF));
		sequenceFlow.setTargetRef(getAttribute(element, ATTR_TARGETREF));
		sequenceFlow.setG(getAttribute(element, ATTR_G));
		sequenceFlow.setOffset(getAttribute(element, ATTR_OFFSET));

		Element conditionElement = DomUtils.listFirstChildElement(element, ELEMENT_CONDITIONEXPRESSION);
		if (null != conditionElement)
		{
			String condition = conditionElement.getFirstChild().getTextContent();
			condition = StringUtils.trimToNull(condition);

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
