package com.liteworkflow.engine.model;

import java.util.List;

/**
 * 任务定义task元素
 * 
 * @version
 */
public class UserTaskModel extends ActivityModel
{
	/**
	 * 参与者变量名称
	 */
	private String assignee;

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
	 * 事件监听器
	 */
	private List<EventListenerElement> eventListeners;

	/**
	 * 获取assignee
	 * 
	 * @return
	 */
	public String getAssignee()
	{
		return assignee;
	}

	/**
	 * 设置assignee
	 * 
	 * @param assignee
	 */
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}

	/**
	 * 获取expireTime
	 * 
	 * @return
	 */
	public String getExpireTime()
	{
		return expireTime;
	}

	/**
	 * 设置expireTime
	 * 
	 * @param expireTime
	 */
	public void setExpireTime(String expireTime)
	{
		this.expireTime = expireTime;
	}

	/**
	 * 获取reminderTime
	 * 
	 * @return
	 */
	public String getReminderTime()
	{
		return reminderTime;
	}

	/**
	 * 设置reminderTime
	 * 
	 * @param reminderTime
	 */
	public void setReminderTime(String reminderTime)
	{
		this.reminderTime = reminderTime;
	}

	/**
	 * 获取reminderRepeat
	 * 
	 * @return
	 */
	public String getReminderRepeat()
	{
		return reminderRepeat;
	}

	/**
	 * 设置reminderRepeat
	 * 
	 * @param reminderRepeat
	 */
	public void setReminderRepeat(String reminderRepeat)
	{
		this.reminderRepeat = reminderRepeat;
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
