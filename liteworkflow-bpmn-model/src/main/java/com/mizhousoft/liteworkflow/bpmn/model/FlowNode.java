package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点元素（存在输入输出的变迁）
 * 
 * @version
 */
public abstract class FlowNode extends FlowElement
{
	/**
	 * 输入变迁集合
	 */
	protected List<SequenceFlowModel> incomingFlows = new ArrayList<>(0);

	/**
	 * 输出变迁集合
	 */
	protected List<SequenceFlowModel> outgoingFlows = new ArrayList<>(0);

	/**
	 * 获取incomingFlows
	 * 
	 * @return
	 */
	public List<SequenceFlowModel> getIncomingFlows()
	{
		return incomingFlows;
	}

	/**
	 * 设置incomingFlows
	 * 
	 * @param incomingFlows
	 */
	public void setIncomingFlows(List<SequenceFlowModel> incomingFlows)
	{
		this.incomingFlows = incomingFlows;
	}

	/**
	 * 获取outgoingFlows
	 * 
	 * @return
	 */
	public List<SequenceFlowModel> getOutgoingFlows()
	{
		return outgoingFlows;
	}

	/**
	 * 设置outgoingFlows
	 * 
	 * @param outgoingFlows
	 */
	public void setOutgoingFlows(List<SequenceFlowModel> outgoingFlows)
	{
		this.outgoingFlows = outgoingFlows;
	}
}
