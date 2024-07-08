package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.StartEventModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 开始节点解析类
 * 
 * @version
 */
public class StartEventParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new StartEventModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "startEvent";
	}
}
