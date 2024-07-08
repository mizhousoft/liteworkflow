package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.parser.AbstractNodeParser;

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
	public String getNodeName()
	{
		return "endEvent";
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
			throw new WorkFlowException("End node can not config transition child element.");
		}
	}

}
