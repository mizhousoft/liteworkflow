package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.JoinModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 合并节点解析类
 * 
 * @version
 */
public class JoinParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected NodeModel newModel()
	{
		return new JoinModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "join";
	}
}
