package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import com.mizhousoft.liteworkflow.bpmn.model.ExclusiveGatewayModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;

/**
 * 决策节点解析类
 * 
 * @version
 */
public class ExclusiveGatewayParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "exclusiveGateway".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new ExclusiveGatewayModel();
	}
}
