package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.List;
import java.util.Set;

/**
 * 任务定义task元素
 * 
 * @version
 */
public class UserTaskModel extends ActivityModel
{
	/**
	 * 表单Key
	 */
	private String formKey;

	/**
	 * 参与者变量名称
	 */
	private String assignee;

	/**
	 * 选择目标类型
	 */
	private SelectTargetType targetType;

	/**
	 * 选择来源，自选一个人或自选多个人
	 */
	private SelectFromEnum selectFrom;

	/**
	 * 可选用户ID
	 */
	private Set<String> optionalUserIds;

	/**
	 * 事件监听器
	 */
	private List<EventListenerElement> eventListeners;

	/**
	 * 获取formKey
	 * 
	 * @return
	 */
	public String getFormKey()
	{
		return formKey;
	}

	/**
	 * 设置formKey
	 * 
	 * @param formKey
	 */
	public void setFormKey(String formKey)
	{
		this.formKey = formKey;
	}

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
	 * 获取targetType
	 * 
	 * @return
	 */
	public SelectTargetType getTargetType()
	{
		return targetType;
	}

	/**
	 * 设置targetType
	 * 
	 * @param targetType
	 */
	public void setTargetType(SelectTargetType targetType)
	{
		this.targetType = targetType;
	}

	/**
	 * 获取selectFrom
	 * 
	 * @return
	 */
	public SelectFromEnum getSelectFrom()
	{
		return selectFrom;
	}

	/**
	 * 设置selectFrom
	 * 
	 * @param selectFrom
	 */
	public void setSelectFrom(SelectFromEnum selectFrom)
	{
		this.selectFrom = selectFrom;
	}

	/**
	 * 获取optionalUserIds
	 * 
	 * @return
	 */
	public Set<String> getOptionalUserIds()
	{
		return optionalUserIds;
	}

	/**
	 * 设置optionalUserIds
	 * 
	 * @param optionalUserIds
	 */
	public void setOptionalUserIds(Set<String> optionalUserIds)
	{
		this.optionalUserIds = optionalUserIds;
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
