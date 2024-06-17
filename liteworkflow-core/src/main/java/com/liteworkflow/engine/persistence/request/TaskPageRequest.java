package com.liteworkflow.engine.persistence.request;

import com.liteworkflow.engine.Constants;
import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * TaskFindRequest
 *
 * @version
 */
public class TaskPageRequest extends PageRequest
{
	private static final long serialVersionUID = -5966541011265844908L;

	/**
	 * 名称
	 */
	private String[] names;

	/**
	 * 流程实例id
	 */
	private String instanceId;

	/**
	 * exclude ids
	 */
	private String[] excludedIds;

	/**
	 * 操作人员id
	 */
	private String[] operators;

	/**
	 * 创建时间范围
	 */
	private String createTimeStart;

	private String createTimeEnd;

	// 排序字段
	private String orderBy = "create_Time";

	// 排序类型ASC/DESC
	private String order = Constants.DESC;

	/**
	 * 构造函数
	 *
	 */
	public TaskPageRequest()
	{
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param instanceId
	 */
	public TaskPageRequest(String instanceId)
	{
		super();
		this.instanceId = instanceId;
	}

	/**
	 * 构造函数
	 *
	 * @param names
	 * @param instanceId
	 * @param excludedIds
	 */
	public TaskPageRequest(String[] names, String instanceId, String[] excludedIds)
	{
		super();
		this.names = names;
		this.instanceId = instanceId;
		this.excludedIds = excludedIds;
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
	 * 获取instanceId
	 * 
	 * @return
	 */
	public String getInstanceId()
	{
		return instanceId;
	}

	/**
	 * 设置instanceId
	 * 
	 * @param instanceId
	 */
	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}

	/**
	 * 获取excludedIds
	 * 
	 * @return
	 */
	public String[] getExcludedIds()
	{
		return excludedIds;
	}

	/**
	 * 设置excludedIds
	 * 
	 * @param excludedIds
	 */
	public void setExcludedIds(String[] excludedIds)
	{
		this.excludedIds = excludedIds;
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
