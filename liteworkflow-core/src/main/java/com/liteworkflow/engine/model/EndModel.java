package com.liteworkflow.engine.model;

import java.util.Collections;
import java.util.List;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.command.EndProcessHandler;

/**
 * 结束节点end元素
 * 
 * @author yuqs
 * @since 1.0
 */
public class EndModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7793175180140842894L;

	public void doExecute(Execution execution)
	{
		fire(new EndProcessHandler(), execution);
	}

	/**
	 * 结束节点无输出变迁
	 */
	public List<TransitionModel> getOutputs()
	{
		return Collections.emptyList();
	}
}
