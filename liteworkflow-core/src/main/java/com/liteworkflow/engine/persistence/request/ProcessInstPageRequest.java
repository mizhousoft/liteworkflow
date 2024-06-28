package com.liteworkflow.engine.persistence.request;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

/**
 * ProcessInstPageRequest
 *
 * @version
 */
public class ProcessInstPageRequest extends PageRequest
{
	private static final long serialVersionUID = -7529904373771692704L;

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
	 * 流程分类
	 */
	private String category;

	/**
	 * 父实例id
	 */
	private String parentId;

	/**
	 * exclude ids
	 */
	private String[] excludedIds;

	/**
	 * 创建时间范围
	 */
	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 构造函数
	 *
	 */
	public ProcessInstPageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.DESC, "o.create_Time"));
	}

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
	 * 获取category
	 * 
	 * @return
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * 设置category
	 * 
	 * @param category
	 */
	public void setCategory(String category)
	{
		this.category = category;
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
}
