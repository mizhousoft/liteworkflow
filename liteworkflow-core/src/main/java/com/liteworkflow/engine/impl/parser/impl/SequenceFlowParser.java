package com.liteworkflow.engine.impl.parser.impl;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.impl.parser.NodeParser;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 顺序流解析器
 *
 * @version
 */
public class SequenceFlowParser
{
	public static FlowElement parse(Element element)
	{
		SequenceFlowModel sequenceFlow = new SequenceFlowModel();

		sequenceFlow.setId(getAttribute(element, NodeParser.ATTR_ID));
		sequenceFlow.setName(getAttribute(element, NodeParser.ATTR_NAME));
		sequenceFlow.setSourceRef(getAttribute(element, "sourceRef"));
		sequenceFlow.setTargetRef(getAttribute(element, "targetRef"));
		sequenceFlow.setG(getAttribute(element, NodeParser.ATTR_G));
		sequenceFlow.setOffset(getAttribute(element, NodeParser.ATTR_OFFSET));

		Element conditionElement = DomUtils.listFirstChildElement(element, "conditionExpression");
		if (null != conditionElement)
		{
			String condition = conditionElement.getFirstChild().getTextContent();
			condition = StringUtils.trimToNull(condition);

			sequenceFlow.setConditionExpression(condition);
		}

		return sequenceFlow;
	}

	public static String getAttribute(Element element, String attrName)
	{
		String value = element.getAttribute(attrName);

		return StringUtils.trimToNull(value);
	}
}
