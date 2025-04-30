package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser;

import org.springframework.util.Assert;
import org.w3c.dom.Element;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;

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
		flowNode.setId(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_ID));
		flowNode.setName(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_NAME));

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
