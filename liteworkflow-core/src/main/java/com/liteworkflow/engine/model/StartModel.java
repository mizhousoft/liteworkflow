package com.liteworkflow.engine.model;

import java.util.Collections;
import java.util.List;

import com.liteworkflow.engine.impl.Execution;

/**
 * 开始节点定义start元素
 * 
 * @author yuqs
 * @since 1.0
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

	protected void doExecute(Execution execution)
	{
		runOutTransition(execution);
	}
}
