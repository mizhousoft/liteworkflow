package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import org.w3c.dom.Element;

import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.BpmnConstant;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.ElementParseUtils;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;

/**
 * 开始节点解析类
 * 
 * @version
 */
public class StartEventParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "startEvent".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParse(FlowNode node, Element element)
	{
		StartEventModel startEvent = (StartEventModel) node;
		startEvent.setInitiator(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_INITIATOR));
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new StartEventModel();
	}
}
