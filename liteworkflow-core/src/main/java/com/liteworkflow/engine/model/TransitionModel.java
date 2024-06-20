package com.liteworkflow.engine.model;

/**
 * 变迁定义transition元素
 * 
 * @author yuqs
 * @since 1.0
 */
public class TransitionModel extends BaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3688123410411321158L;

	/**
	 * 变迁的源节点引用
	 */
	private NodeModel source;

	/**
	 * 变迁的目标节点引用
	 */
	private NodeModel target;

	/**
	 * 变迁的目标节点name名称
	 */
	private String to;

	/**
	 * 变迁的条件表达式，用于decision
	 */
	private String expr;

	/**
	 * 转折点图形数据
	 */
	private String g;

	/**
	 * 描述便宜位置
	 */
	private String offset;

	/**
	 * 当前变迁路径是否可用
	 */
	private boolean enabled = false;

	public NodeModel getSource()
	{
		return source;
	}

	public void setSource(NodeModel source)
	{
		this.source = source;
	}

	public NodeModel getTarget()
	{
		return target;
	}

	public void setTarget(NodeModel target)
	{
		this.target = target;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getExpr()
	{
		return expr;
	}

	public void setExpr(String expr)
	{
		this.expr = expr;
	}

	public String getG()
	{
		return g;
	}

	public void setG(String g)
	{
		this.g = g;
	}

	public String getOffset()
	{
		return offset;
	}

	public void setOffset(String offset)
	{
		this.offset = offset;
	}
}
