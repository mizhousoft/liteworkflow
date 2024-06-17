package com.liteworkflow.engine.persistence.request;

import com.liteworkflow.engine.Constants;
import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * HistoryOrderPageRequest
 *
 * @version
 */
public class HistoricProcessInstPageRequest extends PageRequest
{
	private static final long serialVersionUID = -2630784391027545999L;

	/**
	 * 流程定义id
	 */
	private String processId;

	/**
	 * 操作人员id
	 */
	private String[] operators;

	/**
	 * 名称
	 */
	private String[] names;

	/**
	 * 显示名称
	 */
	private String displayName;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 流程类型
	 */
	private String processType;

	/**
	 * 父实例id
	 */
	private String parentId;

	/**
	 * 创建时间范围
	 */
	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 实例编号
	 */
	private String orderNo;

	// 排序字段
	private String orderBy = "o.create_Time";

	// 排序类型ASC/DESC
	private String order = Constants.DESC;

	/**
	 * 获取processId
	 * 
	 * @return
	 */
	public String getProcessId()
	{
		return processId;
	}

	/**
	 * 设置processId
	 * 
	 * @param processId
	 */
	public void setProcessId(String processId)
	{
		this.processId = processId;
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
	 * 获取parentId
	 * 
	 * @return
	 */
	public String getParentId()
	{
		return parentId;
	}

	/**
	 * 设置parentId
	 * 
	 * @param parentId
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * 获取createTimeStart
	 * 
	 * @return
	 */
	public String getCreateTimeStart()
	{
		return createTimeStart;
	}

	/**
	 * 设置createTimeStart
	 * 
	 * @param createTimeStart
	 */
	public void setCreateTimeStart(String createTimeStart)
	{
		this.createTimeStart = createTimeStart;
	}

	/**
	 * 获取createTimeEnd
	 * 
	 * @return
	 */
	public String getCreateTimeEnd()
	{
		return createTimeEnd;
	}

	/**
	 * 设置createTimeEnd
	 * 
	 * @param createTimeEnd
	 */
	public void setCreateTimeEnd(String createTimeEnd)
	{
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * 获取orderNo
	 * 
	 * @return
	 */
	public String getOrderNo()
	{
		return orderNo;
	}

	/**
	 * 设置orderNo
	 * 
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
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
