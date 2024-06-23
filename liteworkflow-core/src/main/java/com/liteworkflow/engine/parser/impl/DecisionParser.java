package com.liteworkflow.engine.parser.impl;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.DecisionHandler;
import com.liteworkflow.engine.model.DecisionModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;
import com.mizhousoft.commons.lang.ClassUtils;

/**
 * 决策节点解析类
 * 
 * @author
 * @since 1.0
 */
public class DecisionParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected NodeModel newModel()
	{
		return new DecisionModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "decision";
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParseNode(NodeModel node, Element element)
	{
		DecisionModel decision = (DecisionModel) node;
		decision.setExpr(element.getAttribute(ATTR_EXPR));

		String handleClass = element.getAttribute(ATTR_HANDLECLASS);
		decision.setHandleClass(handleClass);
		if (!StringUtils.isBlank(handleClass))
		{
			try
			{
				DecisionHandler decisionHandler = (DecisionHandler) ClassUtils.newInstance(handleClass, this.getClass().getClassLoader());
				decision.setDecisionHandler(decisionHandler);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(handleClass + " is not implment DecisionHandler.", e);
			}
		}
	}

}
