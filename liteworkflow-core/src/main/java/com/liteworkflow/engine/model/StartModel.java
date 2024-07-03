package com.liteworkflow.engine.model;

import java.util.Collections;
import java.util.List;

/**
 * 开始节点定义start元素
 * 
 * @version
 */
public class StartModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4550530562581330477L;

	/**
	 * 开始节点无输入变迁
	 */
	public List<TransitionModel> getInputs()
	{
		return Collections.emptyList();
	}
}
