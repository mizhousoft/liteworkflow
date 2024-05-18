package com.liteworkflow.engine.parser.impl;

import org.w3c.dom.Element;

import com.liteworkflow.engine.model.CustomModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

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
