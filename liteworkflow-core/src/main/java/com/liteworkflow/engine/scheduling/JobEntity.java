package com.liteworkflow.engine.scheduling;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

import com.liteworkflow.engine.persistence.entity.Task;

/**
 * job实体，用于传递给具体的调度框架
 * 
 * @author
 * @since 1.4
 */
public class JobEntity implements Serializable
{
	private static final long serialVersionUID = 5807718947643229134L;

	/**
	 * 参与类型
	 */
	public enum JobType
	{
		EXECUTER, REMINDER;
	}

	/**
	 * job主键
	 */
	private String key;

	/**
	 * job组
	 */
	private String group;

	/**
	 * 任务对应的业务id串
	 */
	private String id;

	/**
	 * 节点模型名称
	 */
	private String modelName;

	/**
	 * job类型
	 */
	private int jobType;

	/**
	 * 任务对象
	 */
	private Task task;

	/**
	 * 启动时间
	 */
	private LocalDate startTime;

	/**
	 * 间隔时间(分钟)
	 */
	private int period;

	/**
	 * 执行参数
	 */
	private Map<String, Object> args;

	public JobEntity(String id, Task task, LocalDate startTime)
	{
		this(id, task, startTime, 0);
	}

	public JobEntity(String id, Task task, LocalDate startTime, int period)
	{
		this.id = id;
		this.task = task;
		this.startTime = startTime;
		this.period = period;
	}

	public JobEntity(String id, Task task, LocalDate startTime, Map<String, Object> args)
	{
		this(id, task, startTime, args, 0);
	}

	public JobEntity(String id, Task task, LocalDate startTime, Map<String, Object> args, int period)
	{
		this.id = id;
		this.task = task;
		this.startTime = startTime;
		this.period = period;
		this.args = args;
	}

	public Task getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task = task;
	}

	/**
	 * 获取startTime
	 * 
	 * @return
	 */
	public LocalDate getStartTime()
	{
		return startTime;
	}

	/**
	 * 设置startTime
	 * 
	 * @param startTime
	 */
	public void setStartTime(LocalDate startTime)
	{
		this.startTime = startTime;
	}

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
	}

	public Map<String, Object> getArgs()
	{
		return args;
	}

	public void setArgs(Map<String, Object> args)
	{
		this.args = args;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getModelName()
	{
		return modelName;
	}

	public void setModelName(String modelName)
	{
		this.modelName = modelName;
	}

	public int getJobType()
	{
		return jobType;
	}

	public void setJobType(int jobType)
	{
		this.jobType = jobType;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}
}
