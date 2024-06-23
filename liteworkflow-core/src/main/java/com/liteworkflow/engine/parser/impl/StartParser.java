package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 开始节点解析类
 * 
 * @author
 * @since 1.0
 */
public class StartParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected NodeModel newModel()
	{
		return new StartModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "start";
	}
}
