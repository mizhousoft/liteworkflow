package org.snaker.engine.parser.impl;

import org.snaker.engine.model.EndModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.AbstractNodeParser;

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
