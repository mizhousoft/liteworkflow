package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 顺序流模型
 *
 * @version
 */
public class SequenceFlowModel extends FlowElement
{
	/**
	 * 变迁的源节点引用
	 */
	private FlowNode sourceNode;

	/**
	 * 变迁的目标节点引用
	 */
	private FlowNode targetNode;

	/**
	 * 变迁的源节点name名称
	 */
	private String sourceRef;

	/**
	 * 变迁的目标节点name名称
	 */
	private String targetRef;

	/**
	 * 变迁的条件表达式
	 */
	private String conditionExpression;

	/**
	 * 是否默认顺序流
	 * 
	 * @return
	 */
	public boolean isDefaultSequenceFlow()
	{
		return null == conditionExpression;
	}

	/**
	 * 获取sourceNode
	 * 
	 * @return
	 */
	public FlowNode getSourceNode()
	{
		return sourceNode;
	}

	/**
	 * 设置sourceNode
	 * 
	 * @param sourceNode
	 */
	public void setSourceNode(FlowNode sourceNode)
	{
		this.sourceNode = sourceNode;
	}

	/**
	 * 获取targetNode
	 * 
	 * @return
	 */
	public FlowNode getTargetNode()
	{
		return targetNode;
	}

	/**
	 * 设置targetNode
	 * 
	 * @param targetNode
	 */
	public void setTargetNode(FlowNode targetNode)
	{
		this.targetNode = targetNode;
	}

	/**
	 * 获取sourceRef
	 * 
	 * @return
	 */
	public String getSourceRef()
	{
		return sourceRef;
	}

	/**
	 * 设置sourceRef
	 * 
	 * @param sourceRef
	 */
	public void setSourceRef(String sourceRef)
	{
		this.sourceRef = sourceRef;
	}

	/**
	 * 获取targetRef
	 * 
	 * @return
	 */
	public String getTargetRef()
	{
		return targetRef;
	}

	/**
	 * 设置targetRef
	 * 
	 * @param targetRef
	 */
	public void setTargetRef(String targetRef)
	{
		this.targetRef = targetRef;
	}

	/**
	 * 获取conditionExpression
	 * 
	 * @return
	 */
	public String getConditionExpression()
	{
		return conditionExpression;
	}

	/**
	 * 设置conditionExpression
	 * 
	 * @param conditionExpression
	 */
	public void setConditionExpression(String conditionExpression)
	{
		this.conditionExpression = conditionExpression;
	}
}
