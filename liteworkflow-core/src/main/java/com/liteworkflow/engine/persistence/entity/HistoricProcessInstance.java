package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.liteworkflow.engine.helper.JsonHelper;

/**
 * 历史流程实例实体类
 * 
 * @author
 * @since 1.0
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
	 * 流程定义ID
	 */
	private String processId;

	/**
	 * 流程实例状态（0：结束；1：活动）
	 */
	private Integer state;

	/**
	 * 流程实例创建者ID
	 */
	private String creator;

	/**
	 * 流程实例创建时间
	 */
	private String createTime;

	/**
	 * 流程实例结束时间
	 */
	private String endTime;

	/**
	 * 流程实例为子流程时，该字段标识父流程实例ID
	 */
	private String parentId;

	/**
	 * 流程实例期望完成时间
	 */
	private String expireTime;

	/**
	 * 流程实例优先级
	 */
	private Integer priority;

	/**
	 * 流程实例编号
	 */
	private String orderNo;

	/**
	 * 流程实例附属变量
	 */
	private String variable;

	public HistoricProcessInstance()
	{

	}

	public HistoricProcessInstance(ProcessInstance instance)
	{
		this.id = instance.getId();
		this.processId = instance.getProcessId();
		this.createTime = instance.getCreateTime();
		this.expireTime = instance.getExpireTime();
		this.creator = instance.getCreator();
		this.parentId = instance.getParentId();
		this.priority = instance.getPriority();
		this.orderNo = instance.getOrderNo();
		this.variable = instance.getVariable();
	}

	/**
	 * 根据历史实例撤回活动实例
	 * 
	 * @return 活动实例对象
	 */
	public ProcessInstance undo()
	{
		ProcessInstance instance = new ProcessInstance();
		instance.setId(this.id);
		instance.setProcessId(this.processId);
		instance.setParentId(this.parentId);
		instance.setCreator(this.creator);
		instance.setCreateTime(this.createTime);
		instance.setLastUpdator(this.creator);
		instance.setLastUpdateTime(this.endTime);
		instance.setExpireTime(this.expireTime);
		instance.setOrderNo(this.orderNo);
		instance.setPriority(this.priority);
		instance.setVariable(this.variable);
		instance.setVersion(0);
		return instance;
	}

	public String getProcessId()
	{
		return processId;
	}

	public void setProcessId(String processId)
	{
		this.processId = processId;
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

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getExpireTime()
	{
		return expireTime;
	}

	public void setExpireTime(String expireTime)
	{
		this.expireTime = expireTime;
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

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getVariable()
	{
		return variable;
	}

	public void setVariable(String variable)
	{
		this.variable = variable;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getVariableMap()
	{
		Map<String, Object> map = JsonHelper.fromJson(this.variable, Map.class);
		if (map == null)
			return Collections.emptyMap();
		return map;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("HistoricProcessInstance(id=").append(this.id);
		sb.append(",processId=").append(this.processId);
		sb.append(",creator=").append(this.creator);
		sb.append(",createTime").append(this.createTime);
		sb.append(",orderNo=").append(this.orderNo).append(")");
		return sb.toString();
	}
}
