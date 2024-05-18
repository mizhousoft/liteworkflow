package org.snaker.engine.parser.impl;

import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.StartModel;
import org.snaker.engine.parser.AbstractNodeParser;

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
