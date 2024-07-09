package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.liteworkflow.engine.model.UserTaskModel;

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
	 * 任务定义ID
	 */
	private String taskDefinitionId;

	/**
	 * 任务名称
	 */
	private String name;

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
	 * 保持模型对象
	 */
	private UserTaskModel model;

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
	 * 获取model
	 * 
	 * @return
	 */
	public UserTaskModel getModel()
	{
		return model;
	}

	/**
	 * 设置model
	 * 
	 * @param model
	 */
	public void setModel(UserTaskModel model)
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
		        .append("\", \"taskDefinitionId\":\"")
		        .append(taskDefinitionId)
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\"}");
		return builder.toString();
	}
}
