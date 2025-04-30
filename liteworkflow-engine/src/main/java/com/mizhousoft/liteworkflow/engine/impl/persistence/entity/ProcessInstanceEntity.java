package com.mizhousoft.liteworkflow.engine.impl.persistence.entity;

import java.time.LocalDateTime;

import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;

/**
 * 流程实例
 * 
 * @version
 */
public class ProcessInstanceEntity implements ProcessInstance
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
	private int priority;

	/**
	 * 修订版本
	 */
	private int revision = 0;

	/**
	 * 流程实例发起人
	 */
	private String startUserId;

	/**
	 * 流程实例创建时间
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
	public int getPriority()
	{
		return priority;
	}

	/**
	 * 设置priority
	 * 
	 * @param priority
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
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
		        .append("\", \"revision\":\"")
		        .append(revision)
		        .append("\"}");
		return builder.toString();
	}
}
