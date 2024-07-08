package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 合并节点解析类
 * 
 * @version
 */
public class JoinGatewayParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new JoinGatewayModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "joinGateway";
	}
}
