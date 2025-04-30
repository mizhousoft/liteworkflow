package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.ParallelGatewayModel;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;

/**
 * 分支节点解析类
 * 
 * @version
 */
public class ParallelGatewayParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "parallelGateway".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new ParallelGatewayModel();
	}
}
