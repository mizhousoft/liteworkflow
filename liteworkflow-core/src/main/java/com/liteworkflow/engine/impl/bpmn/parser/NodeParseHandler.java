package com.liteworkflow.engine.impl.bpmn.parser;

import org.springframework.util.Assert;
import org.w3c.dom.Element;

import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.FlowNode;

/**
 * 抽象dom节点解析类
 * 完成通用的属性、变迁解析
 * 
 * @version
 */
public abstract class NodeParseHandler extends ElementParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FlowElement parse(Element element)
	{
		FlowNode flowNode = newFlowNode();
		flowNode.setId(getAttribute(element, ATTR_ID));
		flowNode.setName(getAttribute(element, ATTR_NAME));
		flowNode.setLayout(getAttribute(element, ATTR_LAYOUT));

		Assert.notNull(flowNode.getId(), "FlowNode id is null, node name is " + element.getNodeName());
		Assert.notNull(flowNode.getName(), "FlowNode name is null, node name is " + element.getNodeName());

		doParse(flowNode, element);

		return flowNode;
	}

	/**
	 * 子类可覆盖此方法，完成特定的解析
	 * 
	 * @param flowNode
	 * @param element
	 */
	protected void doParse(FlowNode flowNode, Element element)
	{

	}

	/**
	 * 抽象方法，由子类产生各自的模型对象
	 * 
	 * @return
	 */
	protected abstract FlowNode newFlowNode();
}
