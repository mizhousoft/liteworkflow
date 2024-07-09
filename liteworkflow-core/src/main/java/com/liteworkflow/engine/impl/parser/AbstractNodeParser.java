package com.liteworkflow.engine.impl.parser;

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
public abstract class AbstractNodeParser implements NodeParser
{
	/**
	 * 模型对象
	 */
	protected FlowNode model;

	/**
	 * {@inheritDoc}
	 */
	public FlowElement getModel()
	{
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parse(Element element)
	{
		model = newModel();
		model.setId(element.getAttribute(ATTR_ID));
		model.setName(element.getAttribute(ATTR_NAME));
		model.setLayout(element.getAttribute(ATTR_LAYOUT));

		doParseNode(model, element);

		checkModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkModel()
	{
		Assert.notNull(model.getId(), "Node id is null.");
		Assert.notNull(model.getName(), "Node display name is null.");
	}

	/**
	 * 子类可覆盖此方法，完成特定的解析
	 * 
	 * @param nodeModel
	 * @param element
	 */
	protected void doParseNode(FlowNode nodeModel, Element element)
	{

	}

	/**
	 * 抽象方法，由子类产生各自的模型对象
	 * 
	 * @return
	 */
	protected abstract FlowNode newModel();
}
