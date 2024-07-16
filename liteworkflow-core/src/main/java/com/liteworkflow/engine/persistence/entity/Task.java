package com.liteworkflow.engine.persistence.entity;

import java.time.LocalDateTime;

/**
 * 任务实体类
 * 
 * @version
 */
public class Task extends VariableScope
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
	private String taskDefinitionId;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 执行人
	 */
	private String assignee;

	/**
	 * 任务类型（0：主办任务；1：协办任务）
	 */
	private Integer taskType;

	/**
	 * 期望任务完成时间
	 */
	private LocalDateTime expireTime;

	/**
	 * 任务附属变量
	 */
	private String variable;

	/**
	 * 修订版本
	 */
	private int revision = 0;

	/**
	 * 任务创建时间
	 */
	private LocalDateTime createTime;

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
	 * 获取taskType
	 * 
	 * @return
	 */
	public Integer getTaskType()
	{
		return taskType;
	}

	/**
	 * 设置taskType
	 * 
	 * @param taskType
	 */
	public void setTaskType(Integer taskType)
	{
		this.taskType = taskType;
	}

	/**
	 * 获取expireTime
	 * 
	 * @return
	 */
	public LocalDateTime getExpireTime()
	{
		return expireTime;
	}

	/**
	 * 设置expireTime
	 * 
	 * @param expireTime
	 */
	public void setExpireTime(LocalDateTime expireTime)
	{
		this.expireTime = expireTime;
	}

	/**
	 * 获取variable
	 * 
	 * @return
	 */
	public String getVariable()
	{
		return variable;
	}

	/**
	 * 设置variable
	 * 
	 * @param variable
	 */
	public void setVariable(String variable)
	{
		this.variable = variable;
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
	 * 获取createTime
	 * 
	 * @return
	 */
	public LocalDateTime getCreateTime()
	{
		return createTime;
	}

	/**
	 * 设置createTime
	 * 
	 * @param createTime
	 */
	public void setCreateTime(LocalDateTime createTime)
	{
		this.createTime = createTime;
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
		        .append("\", \"taskDefinitionId\":\"")
		        .append(taskDefinitionId)
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\"}");
		return builder.toString();
	}
}
