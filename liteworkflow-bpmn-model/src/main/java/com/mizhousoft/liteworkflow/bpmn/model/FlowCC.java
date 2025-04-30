package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.Set;

/**
 * 抄送人
 *
 */
public class FlowCC
{
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 值
	 */
	private String value;

	/**
	 * 抄送用户ID
	 */
	private Set<String> ccUserIds;

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
	 * 获取ccUserIds
	 * 
	 * @return
	 */
	public Set<String> getCcUserIds()
	{
		return ccUserIds;
	}

	/**
	 * 设置ccUserIds
	 * 
	 * @param ccUserIds
	 */
	public void setCcUserIds(Set<String> ccUserIds)
	{
		this.ccUserIds = ccUserIds;
	}
}
