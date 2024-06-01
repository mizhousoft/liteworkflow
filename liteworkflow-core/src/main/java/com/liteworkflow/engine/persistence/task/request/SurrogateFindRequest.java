package com.liteworkflow.engine.persistence.task.request;

import com.liteworkflow.engine.Constants;

/**
 * SurrogateFindRequest
 *
 * @version
 */
public class SurrogateFindRequest
{
	/**
	 * 名称
	 */
	private String[] names;

	/**
	 * 操作人员id
	 */
	private String[] operators;

	private String operateTime;

	// 排序字段
	private String orderBy = "sdate";

	// 排序类型ASC/DESC
	private String order = Constants.DESC;

	/**
	 * 获取names
	 * 
	 * @return
	 */
	public String[] getNames()
	{
		return names;
	}

	/**
	 * 设置names
	 * 
	 * @param names
	 */
	public void setNames(String[] names)
	{
		this.names = names;
	}

	/**
	 * 获取operators
	 * 
	 * @return
	 */
	public String[] getOperators()
	{
		return operators;
	}

	/**
	 * 设置operators
	 * 
	 * @param operators
	 */
	public void setOperators(String[] operators)
	{
		this.operators = operators;
	}

	/**
	 * 获取operateTime
	 * 
	 * @return
	 */
	public String getOperateTime()
	{
		return operateTime;
	}

	/**
	 * 设置operateTime
	 * 
	 * @param operateTime
	 */
	public void setOperateTime(String operateTime)
	{
		this.operateTime = operateTime;
	}

	/**
	 * 获取orderBy
	 * 
	 * @return
	 */
	public String getOrderBy()
	{
		return orderBy;
	}

	/**
	 * 设置orderBy
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	/**
	 * 获取order
	 * 
	 * @return
	 */
	public String getOrder()
	{
		return order;
	}

	/**
	 * 设置order
	 * 
	 * @param order
	 */
	public void setOrder(String order)
	{
		this.order = order;
	}
}
