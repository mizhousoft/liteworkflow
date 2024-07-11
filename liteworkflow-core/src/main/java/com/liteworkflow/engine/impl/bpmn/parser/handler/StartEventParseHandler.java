package com.liteworkflow.engine.impl.bpmn.parser.handler;

import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.StartEventModel;

/**
 * 开始节点解析类
 * 
 * @version
 */
public class StartEventParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "startEvent".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new StartEventModel();
	}
}
