package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史任务实体类
 * 
 * @version
 */
public class HistoricTask implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814632180050362450L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 父任务Id
	 */
	private String parentTaskId;

	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;

	/**
	 * 流程实例ID
	 */
	private String instanceId;

	/**
	 * 任务定义ID
	 */
	private String taskDefinitionId;

	/**
	 * 任务显示名称
	 */
	private String displayName;

	/**
	 * 任务类型
	 */
	private Integer taskType;

	/**
	 * 参与方式（0：普通任务；1：参与者fork任务[即：如果10个参与者，需要每个人都要完成，才继续流转]）
	 */
	private Integer performType;

	/**
	 * 任务状态（0：结束；1：活动）
	 */
	private Integer state;

	/**
	 * 任务附属变量
	 */
	private String variable;

	/**
	 * 任务处理者ID
	 */
	private String operator;

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
	public String getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 获取parentTaskId
	 * 
	 * @return
	 */
	public String getParentTaskId()
	{
		return parentTaskId;
	}

	/**
	 * 设置parentTaskId
	 * 
	 * @param parentTaskId
	 */
	public void setParentTaskId(String parentTaskId)
	{
		this.parentTaskId = parentTaskId;
	}

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	public String getProcessDefinitionId()
	{
		return processDefinitionId;
	}

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	public void setProcessDefinitionId(String processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
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
	 * 获取performType
	 * 
	 * @return
	 */
	public Integer getPerformType()
	{
		return performType;
	}

	/**
	 * 设置performType
	 * 
	 * @param performType
	 */
	public void setPerformType(Integer performType)
	{
		this.performType = performType;
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
	 * 获取operator
	 * 
	 * @return
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * 设置operator
	 * 
	 * @param operator
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
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
		        .append("\", \"displayName\":\"")
		        .append(displayName)
		        .append("\"}");
		return builder.toString();
	}
}
