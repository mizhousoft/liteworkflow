package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.model.EndModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 结束节点解析类
 * 
 * @author
 * @since 1.0
 */
public class EndParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected NodeModel newModel()
	{
		return new EndModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "end";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkModel()
	{
		super.checkModel();

		if (!model.getOutputs().isEmpty())
		{
			throw new ProcessException("End node can not config transition child element.");
		}
	}

}
