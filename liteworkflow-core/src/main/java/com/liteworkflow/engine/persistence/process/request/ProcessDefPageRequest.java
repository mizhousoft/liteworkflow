package com.liteworkflow.engine.persistence.process.request;

import com.liteworkflow.engine.Constants;
import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * ProcessFindRequest
 *
 * @version
 */
public class ProcessDefPageRequest extends PageRequest
{
	private static final long serialVersionUID = -4195498843400032234L;

	/**
	 * 名称
	 */
	private String[] names;

	/**
	 * 流程定义版本号
	 */
	private Integer version;

	/**
	 * 显示名称
	 */
	private String displayName;

	/**
	 * 操作人员id
	 */
	private String[] operators;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 流程类型
	 */
	private String processType;

	// 排序字段
	private String orderBy = "name";

	// 排序类型ASC/DESC
	private String order = Constants.ASC;

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
	 * 获取version
	 * 
	 * @return
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * 设置version
	 * 
	 * @param version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * 获取displayName
	 * 
	 * @return
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * 设置displayName
	 * 
	 * @param displayName
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
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
	 * 获取state
	 * 
	 * @return
	 */
	public Integer getState()
	{
		return state;
	}

	/**
	 * 设置state
	 * 
	 * @param state
	 */
	public void setState(Integer state)
	{
		this.state = state;
	}

	/**
	 * 获取processType
	 * 
	 * @return
	 */
	public String getProcessType()
	{
		return processType;
	}

	/**
	 * 设置processType
	 * 
	 * @param processType
	 */
	public void setProcessType(String processType)
	{
		this.processType = processType;
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
