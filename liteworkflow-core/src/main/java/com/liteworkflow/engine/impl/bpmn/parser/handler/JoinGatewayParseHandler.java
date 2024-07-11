package com.liteworkflow.engine.impl.bpmn.parser.handler;

import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.FlowNode;

/**
 * 合并节点解析类
 * 
 * @version
 */
public class JoinGatewayParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "joinGateway".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new JoinGatewayModel();
	}
}
