package com.liteworkflow.engine.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 任务定义task元素
 * 
 * @version
 */
public class UserTaskModel extends ActivityModel
{
	/**
	 * 类型：普通任务
	 */
	public static final String PERFORMTYPE_ANY = "ANY";

	/**
	 * 类型：参与者fork任务
	 */
	public static final String PERFORMTYPE_ALL = "ALL";

	/**
	 * 参与类型
	 */
	public enum PerformType
	{
		ANY, ALL;
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
	 * 事件监听器
	 */
	private List<EventListenerElement> eventListeners;

	public boolean isPerformAny()
	{
		return PERFORMTYPE_ANY.equalsIgnoreCase(this.performType);
	}

	public boolean isPerformAll()
	{
		return PERFORMTYPE_ALL.equalsIgnoreCase(this.performType);
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

	/**
	 * 获取eventListeners
	 * 
	 * @return
	 */
	public List<EventListenerElement> getEventListeners()
	{
		return eventListeners;
	}

	/**
	 * 设置eventListeners
	 * 
	 * @param eventListeners
	 */
	public void setEventListeners(List<EventListenerElement> eventListeners)
	{
		this.eventListeners = eventListeners;
	}
}
