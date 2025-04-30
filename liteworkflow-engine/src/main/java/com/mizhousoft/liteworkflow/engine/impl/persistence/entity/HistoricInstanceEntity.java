package com.mizhousoft.liteworkflow.engine.impl.persistence.entity;

import java.time.LocalDateTime;

import com.mizhousoft.liteworkflow.engine.domain.HistoricProcessInstance;

/**
 * 历史流程实例实体类
 * 
 * @version
 */
public class HistoricInstanceEntity implements HistoricProcessInstance
{
	/**
	 * 主键ID
	 */
	private int id;

	/**
	 * 流程实例为子流程时，该字段标识父流程实例ID
	 */
	private int parentId;

	/**
	 * 流程定义ID
	 */
	private int processDefinitionId;

	/**
	 * 业务标识
	 */
	private String businessKey;

	/**
	 * 流程实例优先级
	 */
	private Integer priority;

	/**
	 * 流程实例发起人
	 */
	private String startUserId;

	/**
	 * 修订版本
	 */
	private int revision;

	/**
	 * 耗时，单位是秒
	 */
	private long duration;

	/**
	 * 流程实例开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 流程实例结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 获取parentId
	 * 
	 * @return
	 */
	public int getParentId()
	{
		return parentId;
	}

	/**
	 * 设置parentId
	 * 
	 * @param parentId
	 */
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	public int getProcessDefinitionId()
	{
		return processDefinitionId;
	}

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	public void setProcessDefinitionId(int processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * 获取businessKey
	 * 
	 * @return
	 */
	public String getBusinessKey()
	{
		return businessKey;
	}

	/**
	 * 设置businessKey
	 * 
	 * @param businessKey
	 */
	public void setBusinessKey(String businessKey)
	{
		this.businessKey = businessKey;
	}

	/**
	 * 获取priority
	 * 
	 * @return
	 */
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * 设置priority
	 * 
	 * @param priority
	 */
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	/**
	 * 获取startUserId
	 * 
	 * @return
	 */
	public String getStartUserId()
	{
		return startUserId;
	}

	/**
	 * 设置startUserId
	 * 
	 * @param startUserId
	 */
	public void setStartUserId(String startUserId)
	{
		this.startUserId = startUserId;
	}

	/**
	 * 获取revision
	 * 
	 * @return
	 */
	public int getRevision()
	{
		return revision;
	}

	/**
	 * 设置revision
	 * 
	 * @param revision
	 */
	public void setRevision(int revision)
	{
		this.revision = revision;
	}

	/**
	 * 获取duration
	 * 
	 * @return
	 */
	public long getDuration()
	{
		return duration;
	}

	/**
	 * 设置duration
	 * 
	 * @param duration
	 */
	public void setDuration(long duration)
	{
		this.duration = duration;
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

	/**
	 * 获取status
	 * 
	 * @return
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"").append(id).append("\", \"processDefinitionId\":\"").append(processDefinitionId).append("\"}");
		return builder.toString();
	}
}
