package com.liteworkflow.engine.parser.impl;

import com.liteworkflow.engine.model.JoinModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 合并节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class JoinParser extends AbstractNodeParser
{
	/**
	 * 产生JoinModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new JoinModel();
	}
}
