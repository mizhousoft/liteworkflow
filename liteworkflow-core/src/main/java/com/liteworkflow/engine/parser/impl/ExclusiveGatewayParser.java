package com.liteworkflow.engine.parser.impl;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.DecisionHandler;
import com.liteworkflow.engine.model.ExclusiveGatewayModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.parser.AbstractNodeParser;
import com.mizhousoft.commons.lang.ClassUtils;

/**
 * 决策节点解析类
 * 
 * @version
 */
public class ExclusiveGatewayParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new ExclusiveGatewayModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "exclusiveGateway";
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParseNode(FlowNode node, Element element)
	{
		ExclusiveGatewayModel decision = (ExclusiveGatewayModel) node;
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
