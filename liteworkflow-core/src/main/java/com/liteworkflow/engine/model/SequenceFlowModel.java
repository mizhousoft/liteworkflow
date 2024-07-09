package com.liteworkflow.engine.model;

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
	 * 转折点图形数据
	 */
	private String g;

	/**
	 * 描述节点位置
	 */
	private String offset;

	/**
	 * 当前变迁路径是否可用
	 */
	private boolean enabled = false;

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

	/**
	 * 获取g
	 * 
	 * @return
	 */
	public String getG()
	{
		return g;
	}

	/**
	 * 设置g
	 * 
	 * @param g
	 */
	public void setG(String g)
	{
		this.g = g;
	}

	/**
	 * 获取offset
	 * 
	 * @return
	 */
	public String getOffset()
	{
		return offset;
	}

	/**
	 * 设置offset
	 * 
	 * @param offset
	 */
	public void setOffset(String offset)
	{
		this.offset = offset;
	}

	/**
	 * 获取enabled
	 * 
	 * @return
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * 设置enabled
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
