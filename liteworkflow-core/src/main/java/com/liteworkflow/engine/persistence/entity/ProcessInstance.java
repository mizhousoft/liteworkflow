package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.liteworkflow.engine.helper.JsonHelper;

/**
 * 流程工作单实体类（一般称为流程实例）
 * 
 * @author
 * @since 1.0
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
	 * 修订版本
	 */
	private Integer revision = 0;

	/**
	 * 流程定义ID
	 */
	private String processId;

	/**
	 * 流程实例创建者ID
	 */
	private String creator;

	/**
	 * 流程实例创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 流程实例为子流程时，该字段标识父流程实例ID
	 */
	private String parentId;

	/**
	 * 流程实例为子流程时，该字段标识父流程哪个节点模型启动的子流程
	 */
	private String parentNodeName;

	/**
	 * 流程实例期望完成时间
	 */
	private LocalDateTime expireTime;

	/**
	 * 流程实例上一次更新时间
	 */
	private LocalDateTime lastUpdateTime;

	/**
	 * 流程实例上一次更新人员ID
	 */
	private String lastUpdator;

	/**
	 * 流程实例优先级
	 */
	private Integer priority;

	/**
	 * 流程实例附属变量
	 */
	private String variable;

	public String getProcessId()
	{
		return processId;
	}

	public void setProcessId(String processId)
	{
		this.processId = processId;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getLastUpdator()
	{
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator)
	{
		this.lastUpdator = lastUpdator;
	}

	public Integer getPriority()
	{
		return priority;
	}

	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParentNodeName()
	{
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName)
	{
		this.parentNodeName = parentNodeName;
	}

	public String getVariable()
	{
		return variable;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getVariableMap()
	{
		Map<String, Object> map = JsonHelper.fromJson(this.variable, Map.class);
		if (map == null)
			return Collections.emptyMap();
		return map;
	}

	public void setVariable(String variable)
	{
		this.variable = variable;
	}

	/**
	 * 获取revision
	 * 
	 * @return
	 */
	public Integer getRevision()
	{
		return revision;
	}

	/**
	 * 设置revision
	 * 
	 * @param revision
	 */
	public void setRevision(Integer revision)
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
	 * 获取lastUpdateTime
	 * 
	 * @return
	 */
	public LocalDateTime getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * 设置lastUpdateTime
	 * 
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(LocalDateTime lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
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

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ProcessInstance(id=").append(this.id);
		sb.append(",processId=").append(this.processId);
		sb.append(",creator=").append(this.creator);
		sb.append(",createTime").append(this.createTime).append(")");
		return sb.toString();
	}
}
