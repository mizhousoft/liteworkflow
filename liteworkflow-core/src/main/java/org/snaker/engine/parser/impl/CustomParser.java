package org.snaker.engine.parser.impl;

import org.snaker.engine.model.CustomModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.AbstractNodeParser;
import org.w3c.dom.Element;

/**
 * 自定义节点解析器
 * 
 * @author yuqs
 * @since 1.0
 */
public class CustomParser extends AbstractNodeParser
{
	protected void parseNode(NodeModel node, Element element)
	{
		CustomModel custom = (CustomModel) node;
		custom.setClazz(element.getAttribute(ATTR_CLAZZ));
		custom.setMethodName(element.getAttribute(ATTR_METHODNAME));
		custom.setArgs(element.getAttribute(ATTR_ARGS));
		custom.setVar(element.getAttribute(ATTR_VAR));
	}

	protected NodeModel newModel()
	{
		return new CustomModel();
	}

}
