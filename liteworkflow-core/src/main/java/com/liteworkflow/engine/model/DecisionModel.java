package com.liteworkflow.engine.model;

import com.liteworkflow.engine.DecisionHandler;

/**
 * 决策定义decision元素
 * 
 * @author
 * @since 1.0
 */
public class DecisionModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -806621814645169999L;

	/**
	 * 决策选择表达式串（需要表达式引擎解析）
	 */
	private String expr;

	/**
	 * 决策处理类，对于复杂的分支条件，可通过handleClass来处理
	 */
	private String handleClass;

	/**
	 * 决策处理类实例
	 */
	private DecisionHandler decisionHandler;

	public String getExpr()
	{
		return expr;
	}

	public void setExpr(String expr)
	{
		this.expr = expr;
	}

	public String getHandleClass()
	{
		return handleClass;
	}

	public void setHandleClass(String handleClass)
	{
		this.handleClass = handleClass;
	}

	/**
	 * 获取decisionHandler
	 * 
	 * @return
	 */
	public DecisionHandler getDecisionHandler()
	{
		return decisionHandler;
	}

	/**
	 * 设置decisionHandler
	 * 
	 * @param decisionHandler
	 */
	public void setDecisionHandler(DecisionHandler decisionHandler)
	{
		this.decisionHandler = decisionHandler;
	}
}
