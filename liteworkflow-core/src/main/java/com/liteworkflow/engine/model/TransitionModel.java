package com.liteworkflow.engine.model;

/**
 * 变迁定义transition元素
 * 
 * @author
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

	/**
	 * 获取source
	 * @return
	 */
	public NodeModel getSource()
	{
		return source;
	}

	/**
	 * 设置source
	 * @param source
	 */
	public void setSource(NodeModel source)
	{
		this.source = source;
	}

	/**
	 * 获取target
	 * @return
	 */
	public NodeModel getTarget()
	{
		return target;
	}

	/**
	 * 设置target
	 * @param target
	 */
	public void setTarget(NodeModel target)
	{
		this.target = target;
	}

	/**
	 * 获取to
	 * @return
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * 设置to
	 * @param to
	 */
	public void setTo(String to)
	{
		this.to = to;
	}

	/**
	 * 获取expr
	 * @return
	 */
	public String getExpr()
	{
		return expr;
	}

	/**
	 * 设置expr
	 * @param expr
	 */
	public void setExpr(String expr)
	{
		this.expr = expr;
	}

	/**
	 * 获取g
	 * @return
	 */
	public String getG()
	{
		return g;
	}

	/**
	 * 设置g
	 * @param g
	 */
	public void setG(String g)
	{
		this.g = g;
	}

	/**
	 * 获取offset
	 * @return
	 */
	public String getOffset()
	{
		return offset;
	}

	/**
	 * 设置offset
	 * @param offset
	 */
	public void setOffset(String offset)
	{
		this.offset = offset;
	}

	/**
	 * 获取enabled
	 * @return
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * 设置enabled
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
