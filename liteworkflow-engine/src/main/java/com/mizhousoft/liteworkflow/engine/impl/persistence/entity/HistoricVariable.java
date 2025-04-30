package com.mizhousoft.liteworkflow.engine.impl.persistence.entity;

import java.time.LocalDateTime;

/**
 * 历史变量
 *
 * @version
 */
public class HistoricVariable
{
	/**
	 * 主键ID
	 */
	private int id;

	/**
	 * 流程实例ID
	 */
	private int instanceId;

	/**
	 * 任务ID
	 */
	private int taskId;

	/**
	 * 变量名称
	 */
	private String name;

	/**
	 * 变量类型
	 */
	private String type;

	/**
	 * 变量值
	 */
	private String value;

	/**
	 * 修订版本
	 */
	private int revision;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 最后更新时间
	 */
	private LocalDateTime lastUpdateTime;

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
	 * 获取instanceId
	 *
	 * @return
	 */
	public int getInstanceId()
	{
		return instanceId;
	}

	/**
	 * 设置instanceId
	 *
	 * @param instanceId
	 */
	public void setInstanceId(int instanceId)
	{
		this.instanceId = instanceId;
	}

	/**
	 * 获取taskId
	 *
	 * @return
	 */
	public int getTaskId()
	{
		return taskId;
	}

	/**
	 * 设置taskId
	 *
	 * @param taskId
	 */
	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
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
	 * 获取type
	 *
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * 设置type
	 *
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * 获取value
	 *
	 * @return
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * 设置value
	 *
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
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

}