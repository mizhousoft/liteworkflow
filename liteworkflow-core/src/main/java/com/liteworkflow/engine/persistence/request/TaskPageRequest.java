package com.liteworkflow.engine.persistence.request;

import java.time.LocalDateTime;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

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
	 * 操作人员id
	 */
	private String[] operators;

	/**
	 * 创建时间范围
	 */
	private LocalDateTime startTime;

	private LocalDateTime endTime;

	/**
	 * 构造函数
	 *
	 */
	public TaskPageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.DESC, "create_Time"));
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
	 */
	public TaskPageRequest(String[] names, String instanceId)
	{
		super();
		this.names = names;
		this.instanceId = instanceId;
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
	 * 获取startTime
	 * 
	 * @return
	 */
	public LocalDateTime getStartTime()
	{
		return startTime;
	}

	/**
	 * 设置startTime
	 * 
	 * @param startTime
	 */
	public void setStartTime(LocalDateTime startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * 获取endTime
	 * 
	 * @return
	 */
	public LocalDateTime getEndTime()
	{
		return endTime;
	}

	/**
	 * 设置endTime
	 * 
	 * @param endTime
	 */
	public void setEndTime(LocalDateTime endTime)
	{
		this.endTime = endTime;
	}
}
