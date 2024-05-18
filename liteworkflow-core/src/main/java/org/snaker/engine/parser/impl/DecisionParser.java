package org.snaker.engine.parser.impl;

import org.snaker.engine.model.DecisionModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.AbstractNodeParser;
import org.w3c.dom.Element;

/**
 * 决策节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class DecisionParser extends AbstractNodeParser
{
	/**
	 * 产生DecisionModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new DecisionModel();
	}

	/**
	 * 解析decisition节点的特有属性expr
	 */
	protected void parseNode(NodeModel node, Element element)
	{
		DecisionModel decision = (DecisionModel) node;
		decision.setExpr(element.getAttribute(ATTR_EXPR));
		decision.setHandleClass(element.getAttribute(ATTR_HANDLECLASS));
	}
}
