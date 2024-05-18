package org.snaker.engine.parser.impl;

import org.snaker.engine.model.ForkModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.AbstractNodeParser;

/**
 * 分支节点解析类
 * 
 * @author yuqs
 * @since 1.0
 */
public class ForkParser extends AbstractNodeParser
{
	/**
	 * 产生ForkModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new ForkModel();
	}
}
