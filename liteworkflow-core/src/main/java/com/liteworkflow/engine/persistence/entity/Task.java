package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TaskModel.TaskType;

/**
 * 任务实体类
 * 
 * @author
 * @since 1.0
 */
public class Task implements Serializable, Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -189094546633914087L;

	public static final String KEY_ACTOR = "S-ACTOR";

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 版本
	 */
	private Integer version = 0;

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
	 * 参与方式（0：普通任务；1：参与者会签任务）
	 */
	private Integer performType;

	/**
	 * 任务类型（0：主办任务；1：协办任务）
	 */
	private Integer taskType;

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
	 * 期望的完成时间date类型
	 */
	private LocalDate expireDate;

	/**
	 * 提醒时间date类型
	 */
	private LocalDate remindDate;

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

	/**
	 * 保持模型对象
	 */
	private TaskModel model;

	public Task()
	{

	}

	public Task(String id)
	{
		this.id = id;
	}

	public boolean isMajor()
	{
		return this.taskType == TaskType.Major.ordinal();
	}

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

	public String[] getActorIds()
	{
		if (actorIds == null)
		{
			String actorStr = (String) getVariableMap().get(KEY_ACTOR);
			if (actorStr != null)
			{
				actorIds = actorStr.split(",");
			}
		}
		return actorIds;
	}

	public void setActorIds(String[] actorIds)
	{
		this.actorIds = actorIds;
	}

	public Integer getPerformType()
	{
		return performType;
	}

	public void setPerformType(Integer performType)
	{
		this.performType = performType;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * 获取expireDate
	 * 
	 * @return
	 */
	public LocalDate getExpireDate()
	{
		return expireDate;
	}

	/**
	 * 设置expireDate
	 * 
	 * @param expireDate
	 */
	public void setExpireDate(LocalDate expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	 * 获取remindDate
	 * 
	 * @return
	 */
	public LocalDate getRemindDate()
	{
		return remindDate;
	}

	/**
	 * 设置remindDate
	 * 
	 * @param remindDate
	 */
	public void setRemindDate(LocalDate remindDate)
	{
		this.remindDate = remindDate;
	}

	public TaskModel getModel()
	{
		return model;
	}

	public void setModel(TaskModel model)
	{
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getVariableMap()
	{
		Map<String, Object> map = JsonHelper.fromJson(this.variable, Map.class);
		if (map == null)
			return Collections.emptyMap();
		return map;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Task(id=").append(this.id);
		sb.append(",instanceId=").append(this.instanceId);
		sb.append(",taskName=").append(this.taskName);
		sb.append(",displayName").append(this.displayName);
		sb.append(",taskType=").append(this.taskType);
		sb.append(",createTime=").append(this.createTime);
		sb.append(",performType=").append(this.performType).append(")");
		return sb.toString();
	}
}
