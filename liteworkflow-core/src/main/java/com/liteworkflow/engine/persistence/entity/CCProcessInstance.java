package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抄送实例实体
 * 
 * @author
 * @since 1.5
 */
public class CCProcessInstance implements Serializable
{
	private static final long serialVersionUID = -7596174225209412843L;

	private String instanceId;

	private String actorId;

	private String creator;

	private LocalDateTime createTime;

	private LocalDateTime finishTime;

	private Integer status;

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

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getActorId()
	{
		return actorId;
	}

	public void setActorId(String actorId)
	{
		this.actorId = actorId;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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
	 * 获取finishTime
	 * 
	 * @return
	 */
	public LocalDateTime getFinishTime()
	{
		return finishTime;
	}

	/**
	 * 设置finishTime
	 * 
	 * @param finishTime
	 */
	public void setFinishTime(LocalDateTime finishTime)
	{
		this.finishTime = finishTime;
	}
}
