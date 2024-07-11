package com.liteworkflow.engine.impl.bpmn.parser.handler;

import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.EndEventModel;
import com.liteworkflow.engine.model.FlowNode;

/**
 * 结束节点解析类
 * 
 * @version
 */
public class EndEventParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "endEvent".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new EndEventModel();
	}
}
