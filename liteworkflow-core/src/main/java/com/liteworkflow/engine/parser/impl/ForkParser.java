package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.ForkModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 分支节点解析类
 * 
 * @author
 * @since 1.0
 */
public class ForkParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected NodeModel newModel()
	{
		return new ForkModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "fork";
	}
}
