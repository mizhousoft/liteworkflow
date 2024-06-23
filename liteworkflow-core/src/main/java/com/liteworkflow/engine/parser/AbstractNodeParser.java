package com.liteworkflow.engine.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.interceptor.FlowInterceptor;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.mizhousoft.commons.lang.ClassUtils;

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
	 * {@inheritDoc}
	 */
	public NodeModel getModel()
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
		model.setName(element.getAttribute(ATTR_NAME));
		model.setDisplayName(element.getAttribute(ATTR_DISPLAYNAME));
		model.setLayout(element.getAttribute(ATTR_LAYOUT));
		model.setPreInterceptors(element.getAttribute(ATTR_PREINTERCEPTORS));
		model.setPostInterceptors(element.getAttribute(ATTR_POSTINTERCEPTORS));

		List<FlowInterceptor> preInterceptorList = instanceInterceptorList(model.getPreInterceptors());
		model.setPreInterceptorList(preInterceptorList);

		List<FlowInterceptor> postInterceptorList = instanceInterceptorList(model.getPostInterceptors());
		model.setPostInterceptorList(postInterceptorList);

		List<TransitionModel> outputs = parseTransitionModel(element);
		model.setOutputs(outputs);

		doParseNode(model, element);

		checkModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkModel()
	{
		Assert.notNull(model.getName(), "Node name is null.");
		Assert.notNull(model.getDisplayName(), "Node display name is null.");

		List<TransitionModel> outputs = model.getOutputs();
		Set<String> names = new HashSet<>(outputs.size());
		Set<String> tos = new HashSet<>(outputs.size());

		for (TransitionModel output : outputs)
		{
			if (names.contains(output.getName()))
			{
				throw new ProcessException("Transition name duplication, value is " + output.getName());
			}

			if (tos.contains(output.getTo()))
			{
				throw new ProcessException("Transition to duplication, value is " + output.getTo());
			}

			names.add(output.getName());
			tos.add(output.getTo());
		}
	}

	/**
	 * 子类可覆盖此方法，完成特定的解析
	 * 
	 * @param nodeModel
	 * @param element
	 */
	protected void doParseNode(NodeModel nodeModel, Element element)
	{

	}

	/**
	 * 解析transition元素
	 * 
	 * @param element
	 * @return
	 */
	protected List<TransitionModel> parseTransitionModel(Element element)
	{
		List<Element> transitionElements = listChildElements(element, NODE_TRANSITION);
		List<TransitionModel> outputs = new ArrayList<TransitionModel>(transitionElements.size());

		for (Element transitionElement : transitionElements)
		{
			TransitionModel transition = new TransitionModel();
			transition.setName(transitionElement.getAttribute(ATTR_NAME));
			transition.setDisplayName(transitionElement.getAttribute(ATTR_DISPLAYNAME));
			transition.setTo(transitionElement.getAttribute(ATTR_TO));
			transition.setExpr(transitionElement.getAttribute(ATTR_EXPR));
			transition.setG(transitionElement.getAttribute(ATTR_G));
			transition.setOffset(transitionElement.getAttribute(ATTR_OFFSET));
			transition.setSource(model);
			outputs.add(transition);
		}

		return outputs;
	}

	/**
	 * 从element元素查找所有tagName指定的子节点元素集合
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	protected List<Element> listChildElements(Element element, String tagName)
	{
		if (element == null || !element.hasChildNodes())
		{
			return Collections.emptyList();
		}

		List<Element> childElements = new ArrayList<Element>(5);

		for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) child;
				String childTagName = childElement.getNodeName();
				if (tagName.equals(childTagName))
				{
					childElements.add(childElement);
				}
			}
		}

		return childElements;
	}

	/**
	 * 实例化拦截器
	 * 
	 * @param interceptorsStr
	 * @return
	 */
	private List<FlowInterceptor> instanceInterceptorList(String interceptorsStr)
	{
		if (StringUtils.isBlank(interceptorsStr))
		{
			return Collections.emptyList();
		}

		String[] interceptors = interceptorsStr.split(",");

		try
		{
			List<FlowInterceptor> interceptorList = new ArrayList<>(interceptors.length);

			for (String interceptor : interceptors)
			{
				FlowInterceptor instance = (FlowInterceptor) ClassUtils.newInstance(interceptor, this.getClass().getClassLoader());
				interceptorList.add(instance);
			}

			return interceptorList;
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(interceptorsStr + " is not implment FlowInterceptor.", e);
		}
	}

	/**
	 * 抽象方法，由子类产生各自的模型对象
	 * 
	 * @return
	 */
	protected abstract NodeModel newModel();
}
