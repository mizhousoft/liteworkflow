package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.model.TaskModel.PerformType;

/**
 * 历史任务实体类
 * 
 * @author
 * @since 1.0
 */
public class HistoricTask implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6814632180050362450L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 流程实例ID
	 */
	private String instanceId;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 任务显示名称
	 */
	private String displayName;

	/**
	 * 参与方式（0：普通任务；1：参与者fork任务[即：如果10个参与者，需要每个人都要完成，才继续流转]）
	 */
	private Integer performType;

	/**
	 * 任务类型
	 */
	private Integer taskType;

	/**
	 * 任务状态（0：结束；1：活动）
	 */
	private Integer taskState;

	/**
	 * 任务处理者ID
	 */
	private String operator;

	/**
	 * 任务创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 任务完成时间
	 */
	private LocalDateTime finishTime;

	/**
	 * 期望任务完成时间
	 */
	private LocalDateTime expireTime;

	/**
	 * 任务关联的表单url
	 */
	private String actionUrl;

	/**
	 * 任务参与者列表
	 */
	private String[] actorIds;

	/**
	 * 父任务Id
	 */
	private String parentTaskId;

	/**
	 * 任务附属变量
	 */
	private String variable;

	public String getParentTaskId()
	{
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId)
	{
		this.parentTaskId = parentTaskId;
	}

	public String getVariable()
	{
		return variable;
	}

	public void setVariable(String variable)
	{
		this.variable = variable;
	}

	public HistoricTask()
	{

	}

	public HistoricTask(Task task)
	{
		this.id = task.getId();
		this.instanceId = task.getInstanceId();
		this.createTime = task.getCreateTime();
		this.displayName = task.getDisplayName();
		this.taskName = task.getTaskName();
		this.taskType = task.getTaskType();
		this.expireTime = task.getExpireTime();
		this.actionUrl = task.getActionUrl();
		this.actorIds = task.getActorIds();
		this.parentTaskId = task.getParentTaskId();
		this.variable = task.getVariable();
		this.performType = task.getPerformType();
	}

	/**
	 * 根据历史任务产生撤回的任务对象
	 * 
	 * @return 任务对象
	 */
	public Task undoTask()
	{
		Task task = new Task();
		task.setInstanceId(this.getInstanceId());

		task.setTaskName(this.getTaskName());
		task.setDisplayName(this.getDisplayName());
		task.setTaskType(this.getTaskType());
		task.setExpireTime(this.getExpireTime());
		task.setActionUrl(this.getActionUrl());
		task.setParentTaskId(this.getParentTaskId());
		task.setVariable(this.getVariable());
		task.setPerformType(this.getPerformType());
		task.setOperator(this.getOperator());
		return task;
	}

	public boolean isPerformAny()
	{
		return this.performType.intValue() == PerformType.ANY.ordinal();
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public Integer getTaskType()
	{
		return taskType;
	}

	public void setTaskType(Integer taskType)
	{
		this.taskType = taskType;
	}

	public Integer getTaskState()
	{
		return taskState;
	}

	public void setTaskState(Integer taskState)
	{
		this.taskState = taskState;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
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

	public String getActionUrl()
	{
		return actionUrl;
	}

	public void setActionUrl(String actionUrl)
	{
		this.actionUrl = actionUrl;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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

	public Integer getPerformType()
	{
		return performType;
	}

	public void setPerformType(Integer performType)
	{
		this.performType = performType;
	}

	public String[] getActorIds()
	{
		return actorIds;
	}

	public void setActorIds(String[] actorIds)
	{
		this.actorIds = actorIds;
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
		sb.append("HistoricTask(id=").append(this.id);
		sb.append(",instanceId=").append(this.instanceId);
		sb.append(",taskName=").append(this.taskName);
		sb.append(",displayName").append(this.displayName);
		sb.append(",taskType=").append(this.taskType);
		sb.append(",createTime").append(this.createTime);
		sb.append(",performType=").append(this.performType).append(")");
		return sb.toString();
	}
}
