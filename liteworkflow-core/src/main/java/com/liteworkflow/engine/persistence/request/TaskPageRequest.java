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
	 * 流程实例id
	 */
	private int instanceId;

	/**
	 * 任务定义ID
	 */
	private String taskDefinitionId;

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
	 * 获取taskDefinitionId
	 * 
	 * @return
	 */
	public String getTaskDefinitionId()
	{
		return taskDefinitionId;
	}

	/**
	 * 设置taskDefinitionId
	 * 
	 * @param taskDefinitionId
	 */
	public void setTaskDefinitionId(String taskDefinitionId)
	{
		this.taskDefinitionId = taskDefinitionId;
	}

	/**
	 * 获取instanceId
	 * 
	 * @return
	 */
	public int getInstanceId()
	{
		return instanceId;
	}

	/**
	 * 设置instanceId
	 * 
	 * @param instanceId
	 */
	public void setInstanceId(int instanceId)
	{
		this.instanceId = instanceId;
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
