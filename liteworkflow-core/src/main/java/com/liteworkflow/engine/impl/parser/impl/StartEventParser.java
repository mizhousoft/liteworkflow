package com.liteworkflow.engine.impl.parser.impl;

import com.liteworkflow.engine.impl.parser.AbstractNodeParser;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.StartEventModel;

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
	public String getElementName()
	{
		return "startEvent";
	}
}
