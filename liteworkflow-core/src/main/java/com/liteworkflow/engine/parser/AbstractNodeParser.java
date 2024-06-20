package com.liteworkflow.engine.parser;

import java.util.List;

import org.w3c.dom.Element;

import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 抽象dom节点解析类
 * 完成通用的属性、变迁解析
 * 
 * @author
 * @since 1.0
 */
public abstract class AbstractNodeParser implements NodeParser
{
	/**
	 * 模型对象
	 */
	protected NodeModel model;

	/**
	 * 实现NodeParser接口的parse函数
	 * 由子类产生各自的模型对象，设置常用的名称属性，并且解析子节点transition，构造TransitionModel模型对象
	 */
	public void parse(Element element)
	{
		model = newModel();
		model.setName(element.getAttribute(ATTR_NAME));
		model.setDisplayName(element.getAttribute(ATTR_DISPLAYNAME));
		model.setLayout(element.getAttribute(ATTR_LAYOUT));
		model.setPreInterceptors(element.getAttribute(ATTR_PREINTERCEPTORS));
		model.setPostInterceptors(element.getAttribute(ATTR_POSTINTERCEPTORS));

		List<Element> transitions = DomUtils.elements(element, NODE_TRANSITION);
		for (Element te : transitions)
		{
			TransitionModel transition = new TransitionModel();
			transition.setName(te.getAttribute(ATTR_NAME));
			transition.setDisplayName(te.getAttribute(ATTR_DISPLAYNAME));
			transition.setTo(te.getAttribute(ATTR_TO));
			transition.setExpr(te.getAttribute(ATTR_EXPR));
			transition.setG(te.getAttribute(ATTR_G));
			transition.setOffset(te.getAttribute(ATTR_OFFSET));
			transition.setSource(model);
			model.getOutputs().add(transition);
		}

		doParseNode(model, element);
	}

	/**
	 * 子类可覆盖此方法，完成特定的解析
	 * 
	 * @param model
	 * @param element
	 */
	protected void doParseNode(NodeModel model, Element element)
	{

	}

	/**
	 * 抽象方法，由子类产生各自的模型对象
	 * 
	 * @return
	 */
	protected abstract NodeModel newModel();

	/**
	 * 返回模型对象
	 */
	public NodeModel getModel()
	{
		return model;
	}
}
