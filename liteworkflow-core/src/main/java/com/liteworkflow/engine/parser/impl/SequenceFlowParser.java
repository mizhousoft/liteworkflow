package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 顺序流解析器
 *
 * @version
 */
public class SequenceFlowParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "sequenceFlow";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FlowNode newModel()
	{
		return new SequenceFlowModel();
	}
}
