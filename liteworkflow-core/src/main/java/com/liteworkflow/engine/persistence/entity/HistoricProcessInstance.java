package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史流程实例实体类
 * 
 * @version
 */
public class HistoricProcessInstance implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853727929104539328L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 流程实例为子流程时，该字段标识父流程实例ID
	 */
	private String parentId;

	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;

	/**
	 * 业务标识
	 */
	private String businessKey;

	/**
	 * 流程实例状态（0：结束；1：活动）
	 */
	private Integer state;

	/**
	 * 流程实例优先级
	 */
	private Integer priority;

	/**
	 * 流程实例附属变量
	 */
	private String variable;

	/**
	 * 流程实例结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 流程实例创建者ID
	 */
	private String creator;

	/**
	 * 流程实例创建时间
	 */
	private LocalDateTime createTime;

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
	 * 获取creator
	 * 
	 * @return
	 */
	public String getCreator()
	{
		return creator;
	}

	/**
	 * 设置creator
	 * 
	 * @param creator
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
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
		builder.append("{\"id\":\"").append(id).append("\", \"processDefinitionId\":\"").append(processDefinitionId).append("\"}");
		return builder.toString();
	}
}
