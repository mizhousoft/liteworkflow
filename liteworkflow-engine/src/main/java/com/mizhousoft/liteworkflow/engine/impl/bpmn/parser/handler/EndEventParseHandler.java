package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import com.mizhousoft.liteworkflow.bpmn.model.EndEventModel;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;

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
