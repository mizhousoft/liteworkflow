package com.liteworkflow.engine.persistence.order.entity;

import java.io.Serializable;

/**
 * 抄送实例实体
 * 
 * @author yuqs
 * @since 1.5
 */
public class CCOrder implements Serializable
{
	private static final long serialVersionUID = -7596174225209412843L;

	private String orderId;

	private String actorId;

	private String creator;

	private String createTime;

	private String finishTime;

	private Integer status;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
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

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getFinishTime()
	{
		return finishTime;
	}

	public void setFinishTime(String finishTime)
	{
		this.finishTime = finishTime;
	}
}
