package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.EndModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 结束节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class EndParser extends AbstractNodeParser
{
	/**
	 * 产生EndModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new EndModel();
	}
}
