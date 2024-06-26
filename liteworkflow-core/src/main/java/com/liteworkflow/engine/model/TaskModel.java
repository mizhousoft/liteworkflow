package com.liteworkflow.engine.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.liteworkflow.engine.AssignmentHandler;

/**
 * 任务定义task元素
 * 
 * @author
 * @since 1.0
 */
public class TaskModel extends ActivityModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1775545243233990922L;

	/**
	 * 类型：普通任务
	 */
	public static final String PERFORMTYPE_ANY = "ANY";

	/**
	 * 类型：参与者fork任务
	 */
	public static final String PERFORMTYPE_ALL = "ALL";

	/**
	 * 类型：主办任务
	 */
	public static final String TASKTYPE_MAJOR = "Major";

	/**
	 * 类型：协办任务
	 */
	public static final String TASKTYPE_AIDANT = "Aidant";

	/**
	 * 参与类型
	 */
	public enum PerformType
	{
		ANY, ALL;
	}

	/**
	 * 任务类型(Major:主办的,Aidant:协助的,Record:仅仅作为记录的)
	 */
	public enum TaskType
	{
		Major, Aidant, Record;
	}

	/**
	 * 参与者变量名称
	 */
	private String assignee;

	/**
	 * 参与方式
	 * any：任何一个参与者处理完即执行下一步
	 * all：所有参与者都完成，才可执行下一步
	 */
	private String performType = PERFORMTYPE_ANY;

	/**
	 * 任务类型
	 * major：主办任务
	 * aidant：协办任务
	 */
	private String taskType = TASKTYPE_MAJOR;

	/**
	 * 期望完成时间
	 */
	private String expireTime;

	/**
	 * 提醒时间
	 */
	private String reminderTime;

	/**
	 * 提醒间隔(分钟)
	 */
	private String reminderRepeat;

	/**
	 * 是否自动执行
	 */
	private String autoExecute;

	/**
	 * 分配参与者处理类型
	 */
	private String assignmentHandler;

	/**
	 * 分配参与者处理对象
	 */
	private AssignmentHandler assignmentHandlerObject;

	/**
	 * 监听器模型
	 */
	private List<ListenerModel> listenerModels;

	public boolean isPerformAny()
	{
		return PERFORMTYPE_ANY.equalsIgnoreCase(this.performType);
	}

	public boolean isPerformAll()
	{
		return PERFORMTYPE_ALL.equalsIgnoreCase(this.performType);
	}

	public boolean isMajor()
	{
		return TASKTYPE_MAJOR.equalsIgnoreCase(this.taskType);
	}

	public String getAssignee()
	{
		return assignee;
	}

	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}

	public String getExpireTime()
	{
		return expireTime;
	}

	public void setExpireTime(String expireTime)
	{
		this.expireTime = expireTime;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = (StringUtils.isBlank(taskType) ? TASKTYPE_MAJOR : taskType);
	}

	public String getPerformType()
	{
		return performType;
	}

	public void setPerformType(String performType)
	{
		this.performType = (StringUtils.isBlank(performType) ? PERFORMTYPE_ANY : performType);
	}

	public String getReminderTime()
	{
		return reminderTime;
	}

	public void setReminderTime(String reminderTime)
	{
		this.reminderTime = reminderTime;
	}

	public String getReminderRepeat()
	{
		return reminderRepeat;
	}

	public void setReminderRepeat(String reminderRepeat)
	{
		this.reminderRepeat = reminderRepeat;
	}

	public String getAutoExecute()
	{
		return autoExecute;
	}

	public void setAutoExecute(String autoExecute)
	{
		this.autoExecute = autoExecute;
	}

	public AssignmentHandler getAssignmentHandlerObject()
	{
		return assignmentHandlerObject;
	}

	/**
	 * 设置assignmentHandlerObject
	 * 
	 * @param assignmentHandlerObject
	 */
	public void setAssignmentHandlerObject(AssignmentHandler assignmentHandlerObject)
	{
		this.assignmentHandlerObject = assignmentHandlerObject;
	}

	public void setAssignmentHandler(String assignmentHandlerStr)
	{
		this.assignmentHandler = assignmentHandlerStr;
	}

	public String getAssignmentHandler()
	{
		return assignmentHandler;
	}

	/**
	 * 获取listenerModels
	 * 
	 * @return
	 */
	public List<ListenerModel> getListenerModels()
	{
		return listenerModels;
	}

	/**
	 * 设置listenerModels
	 * 
	 * @param listenerModels
	 */
	public void setListenerModels(List<ListenerModel> listenerModels)
	{
		this.listenerModels = listenerModels;
	}
}
