package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.JoinGatewayModel;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;

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
