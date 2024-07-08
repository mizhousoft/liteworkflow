package com.liteworkflow.engine.model;

import java.util.Collections;
import java.util.List;

/**
 * 结束节点end元素
 * 
 * @version
 */
public class EndEventModel extends EventModel
{
	/**
	 * 结束节点无输出变迁
	 */
	public List<TransitionModel> getOutputs()
	{
		return Collections.emptyList();
	}
}
