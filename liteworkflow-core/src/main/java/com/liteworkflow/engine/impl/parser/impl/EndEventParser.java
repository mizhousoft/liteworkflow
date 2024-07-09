package com.liteworkflow.engine.impl.parser.impl;

import com.liteworkflow.engine.impl.parser.AbstractNodeParser;
import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.FlowNode;

/**
 * 结束节点解析类
 * 
 * @version
 */
public class EndEventParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new EndEventModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getElementName()
	{
		return "endEvent";
	}
}
