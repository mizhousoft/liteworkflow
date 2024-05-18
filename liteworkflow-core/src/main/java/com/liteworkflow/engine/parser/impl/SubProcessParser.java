package com.liteworkflow.engine.parser.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.helper.ConfigHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 子流程节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class SubProcessParser extends AbstractNodeParser
{
	/**
	 * 产生SubProcessModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new SubProcessModel();
	}

	/**
	 * 解析decisition节点的特有属性expr
	 */
	protected void parseNode(NodeModel node, Element element)
	{
		SubProcessModel model = (SubProcessModel) node;
		model.setProcessName(element.getAttribute(ATTR_PROCESSNAME));
		String version = element.getAttribute(ATTR_VERSION);
		int ver = 0;
		if (NumberUtils.isCreatable(version))
		{
			ver = Integer.parseInt(version);
		}
		model.setVersion(ver);
		String form = element.getAttribute(ATTR_FORM);
		if (StringHelper.isNotEmpty(form))
		{
			model.setForm(form);
		}
		else
		{
			model.setForm(ConfigHelper.getProperty("subprocessurl"));
		}
	}
}
