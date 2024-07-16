package com.liteworkflow.engine.persistence.entity;

import java.time.LocalDateTime;

/**
 * 历史流程实例实体类
 * 
 * @version
 */
public class HistoricProcessInstance extends VariableScope
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
	 * 流程实例发起人
	 */
	private String initiator;

	/**
	 * 流程实例开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 流程实例结束时间
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
	 * 获取initiator
	 * 
	 * @return
	 */
	public String getInitiator()
	{
		return initiator;
	}

	/**
	 * 设置initiator
	 * 
	 * @param initiator
	 */
	public void setInitiator(String initiator)
	{
		this.initiator = initiator;
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
		builder.append("{\"id\":\"").append(id).append("\", \"processDefinitionId\":\"").append(processDefinitionId).append("\"}");
		return builder.toString();
	}
}
