package com.liteworkflow.engine.model;

import java.util.Collections;
import java.util.List;

/**
 * 开始节点定义start元素
 * 
 * @version
 */
public class StartEventModel extends EventModel
{
	/**
	 * 开始节点无输入变迁
	 */
	public List<TransitionModel> getInputs()
	{
		return Collections.emptyList();
	}
}
