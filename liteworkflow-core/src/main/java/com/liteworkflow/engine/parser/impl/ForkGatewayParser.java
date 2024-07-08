package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 分支节点解析类
 * 
 * @version
 */
public class ForkGatewayParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new ForkGatewayModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "forkGateway";
	}
}
