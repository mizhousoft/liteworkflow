package com.mizhousoft.liteworkflow.engine.impl.persistence.entity;

import java.time.LocalDateTime;

import com.mizhousoft.liteworkflow.engine.domain.HistoricTask;

/**
 * 历史任务
 * 
 * @version
 */
public class HistoricTaskEntity implements HistoricTask
{
	/**
	 * 主键ID
	 */
	private int id;

	/**
	 * 父任务Id
	 */
	private int parentTaskId;

	/**
	 * 流程定义ID
	 */
	private int processDefinitionId;

	/**
	 * 流程实例ID
	 */
	private int instanceId;

	/**
	 * 任务定义ID
	 */
	private String taskDefinitionKey;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 所属人
	 */
	private String owner;

	/**
	 * 执行人
	 */
	private String assignee;

	/**
	 * 任务状态
	 */
	private String status;

	/**
	 * 修订版本
	 */
	private int revision;

	/**
	 * 耗时，单位是秒
	 */
	private long duration;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

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
	 * 获取parentTaskId
	 * 
	 * @return
	 */
	public int getParentTaskId()
	{
		return parentTaskId;
	}

	/**
	 * 设置parentTaskId
	 * 
	 * @param parentTaskId
	 */
	public void setParentTaskId(int parentTaskId)
	{
		this.parentTaskId = parentTaskId;
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
	 * 获取taskDefinitionKey
	 * 
	 * @return
	 */
	public String getTaskDefinitionKey()
	{
		return taskDefinitionKey;
	}

	/**
	 * 设置taskDefinitionKey
	 * 
	 * @param taskDefinitionKey
	 */
	public void setTaskDefinitionKey(String taskDefinitionKey)
	{
		this.taskDefinitionKey = taskDefinitionKey;
	}

	/**
	 * 获取name
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取owner
	 * 
	 * @return
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * 设置owner
	 * 
	 * @param owner
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * 获取assignee
	 * 
	 * @return
	 */
	public String getAssignee()
	{
		return assignee;
	}

	/**
	 * 设置assignee
	 * 
	 * @param assignee
	 */
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"")
		        .append(id)
		        .append("\", \"processDefinitionId\":\"")
		        .append(processDefinitionId)
		        .append("\", \"instanceId\":\"")
		        .append(instanceId)
		        .append("\", \"taskDefinitionKey\":\"")
		        .append(taskDefinitionKey)
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\"}");
		return builder.toString();
	}
}
