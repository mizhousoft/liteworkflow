package com.liteworkflow.engine.impl.parser.impl;

import com.liteworkflow.engine.model.ForkGatewayModel;
import com.liteworkflow.engine.impl.parser.AbstractNodeParser;
import com.liteworkflow.engine.model.FlowNode;

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
	public String getElementName()
	{
		return "forkGateway";
	}
}
