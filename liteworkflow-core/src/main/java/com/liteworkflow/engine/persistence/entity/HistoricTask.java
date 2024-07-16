package com.liteworkflow.engine.persistence.entity;

import java.time.LocalDateTime;

/**
 * 历史任务实体类
 * 
 * @version
 */
public class HistoricTask extends VariableScope
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
	 * 任务类型
	 */
	private Integer taskType;

	/**
	 * 任务状态（0：结束；1：活动）
	 */
	private Integer state;

	/**
	 * 任务附属变量
	 */
	private String variable;

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
		        .append("\", \"taskDefinitionId\":\"")
		        .append(taskDefinitionId)
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\"}");
		return builder.toString();
	}
}
