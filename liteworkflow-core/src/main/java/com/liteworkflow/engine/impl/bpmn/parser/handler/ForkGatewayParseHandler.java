package com.liteworkflow.engine.impl.bpmn.parser.handler;

import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.FlowNode;

/**
 * 分支节点解析类
 * 
 * @version
 */
public class ForkGatewayParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "forkGateway".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new ForkGatewayModel();
	}
}
