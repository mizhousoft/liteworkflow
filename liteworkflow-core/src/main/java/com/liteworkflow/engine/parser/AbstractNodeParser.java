package com.liteworkflow.engine.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;
import org.w3c.dom.Element;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.util.DomUtils;

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
	public FlowNode getModel()
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
		model.setDisplayName(element.getAttribute(ATTR_DISPLAYNAME));
		model.setLayout(element.getAttribute(ATTR_LAYOUT));

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
		Assert.notNull(model.getId(), "Node id is null.");
		Assert.notNull(model.getDisplayName(), "Node display name is null.");

		List<TransitionModel> outputs = model.getOutputs();
		Set<String> ids = new HashSet<>(outputs.size());
		Set<String> tos = new HashSet<>(outputs.size());

		for (TransitionModel output : outputs)
		{
			if (ids.contains(output.getId()))
			{
				throw new WorkFlowException("Transition name duplication, value is " + output.getId());
			}

			if (tos.contains(output.getTo()))
			{
				throw new WorkFlowException("Transition to duplication, value is " + output.getTo());
			}

			ids.add(output.getId());
			tos.add(output.getTo());
		}
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
	 * 解析transition元素
	 * 
	 * @param element
	 * @return
	 */
	protected List<TransitionModel> parseTransitionModel(Element element)
	{
		List<Element> transitionElements = DomUtils.listChildElements(element, NODE_TRANSITION);
		List<TransitionModel> outputs = new ArrayList<TransitionModel>(transitionElements.size());

		for (Element transitionElement : transitionElements)
		{
			TransitionModel transition = new TransitionModel();
			transition.setId(transitionElement.getAttribute(ATTR_ID));
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
	 * 抽象方法，由子类产生各自的模型对象
	 * 
	 * @return
	 */
	protected abstract FlowNode newModel();
}
