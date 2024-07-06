package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.liteworkflow.engine.model.TaskModel;

/**
 * 任务实体类
 * 
 * @version
 */
public class Task implements Serializable, Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -189094546633914087L;

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
	 * 任务名称
	 */
	private String name;

	/**
	 * 任务显示名称
	 */
	private String displayName;

	/**
	 * 任务类型（0：主办任务；1：协办任务）
	 */
	private Integer taskType;

	/**
	 * 参与方式（0：普通任务；1：参与者会签任务）
	 */
	private Integer performType;

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
	 * 任务处理者ID
	 */
	private String operator;

	/**
	 * 任务创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 期望的完成时间date类型
	 */
	private LocalDate expireDate;

	/**
	 * 提醒时间date类型
	 */
	private LocalDate remindDate;

	/**
	 * 保持模型对象
	 */
	private TaskModel model;

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
	 * 获取expireDate
	 * 
	 * @return
	 */
	public LocalDate getExpireDate()
	{
		return expireDate;
	}

	/**
	 * 设置expireDate
	 * 
	 * @param expireDate
	 */
	public void setExpireDate(LocalDate expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	 * 获取remindDate
	 * 
	 * @return
	 */
	public LocalDate getRemindDate()
	{
		return remindDate;
	}

	/**
	 * 设置remindDate
	 * 
	 * @param remindDate
	 */
	public void setRemindDate(LocalDate remindDate)
	{
		this.remindDate = remindDate;
	}

	/**
	 * 获取model
	 * 
	 * @return
	 */
	public TaskModel getModel()
	{
		return model;
	}

	/**
	 * 设置model
	 * 
	 * @param model
	 */
	public void setModel(TaskModel model)
	{
		this.model = model;
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
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\", \"displayName\":\"")
		        .append(displayName)
		        .append("\"}");
		return builder.toString();
	}
}
