package com.liteworkflow.engine.entity;

import java.io.Serializable;

/**
 * 历史任务参与者实体类
 * 
 * @author yuqs
 * @since 1.0
 */
public class HistoryTaskActor implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -998098931519373599L;

	/**
	 * 关联的任务ID
	 */
	private String taskId;

	/**
	 * 关联的参与者ID（参与者可以为用户、部门、角色）
	 */
	private String actorId;

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getActorId()
	{
		return actorId;
	}

	public void setActorId(String actorId)
	{
		this.actorId = actorId;
	}
}
