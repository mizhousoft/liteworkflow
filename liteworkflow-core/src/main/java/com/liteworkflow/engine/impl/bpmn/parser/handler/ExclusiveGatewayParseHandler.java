package com.liteworkflow.engine.impl.bpmn.parser.handler;

import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.ExclusiveGatewayModel;
import com.liteworkflow.engine.model.FlowNode;

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
