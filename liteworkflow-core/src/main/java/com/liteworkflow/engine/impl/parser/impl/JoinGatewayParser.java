package com.liteworkflow.engine.impl.parser.impl;

import com.liteworkflow.engine.model.JoinGatewayModel;
import com.liteworkflow.engine.impl.parser.AbstractNodeParser;
import com.liteworkflow.engine.model.FlowNode;

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
	public String getElementName()
	{
		return "joinGateway";
	}
}
