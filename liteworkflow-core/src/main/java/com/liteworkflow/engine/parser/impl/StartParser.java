package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 开始节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class StartParser extends AbstractNodeParser
{
	/**
	 * 产生StartModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new StartModel();
	}
}
