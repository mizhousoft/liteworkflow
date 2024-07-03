package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 流程实例
 * 
 * @version
 */
public class ProcessInstance implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8335779448165343933L;

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
	 * 流程实例优先级
	 */
	private int priority;

	/**
	 * 流程实例为子流程时，该字段标识父流程哪个节点模型启动的子流程
	 */
	private String parentNodeName;

	/**
	 * 流程实例附属变量
	 */
	private String variable;

	/**
	 * 修订版本
	 */
	private int revision = 0;

	/**
	 * 流程实例创建者ID
	 */
	private String creator;

	/**
	 * 流程实例创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

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
	 * 获取parentNodeName
	 * 
	 * @return
	 */
	public String getParentNodeName()
	{
		return parentNodeName;
	}

	/**
	 * 设置parentNodeName
	 * 
	 * @param parentNodeName
	 */
	public void setParentNodeName(String parentNodeName)
	{
		this.parentNodeName = parentNodeName;
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
	 * 获取variableMap
	 * 
	 * @return
	 */
	public Map<String, Object> getVariableMap()
	{
		return variableMap;
	}

	/**
	 * 设置variableMap
	 * 
	 * @param variableMap
	 */
	public void setVariableMap(Map<String, Object> variableMap)
	{
		this.variableMap = variableMap;
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
