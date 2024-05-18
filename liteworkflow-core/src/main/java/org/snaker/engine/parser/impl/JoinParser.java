package org.snaker.engine.parser.impl;

import org.snaker.engine.model.JoinModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.AbstractNodeParser;

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
