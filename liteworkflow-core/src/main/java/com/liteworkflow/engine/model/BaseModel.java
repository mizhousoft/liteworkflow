package com.liteworkflow.engine.model;

import java.io.Serializable;

/**
 * 模型元素基类
 * 
 * @author
 * @since 1.0
 */
public class BaseModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3082741431225739241L;

	/**
	 * 元素名称
	 */
	private String name;

	/**
	 * 显示名称
	 */
	private String displayName;

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
	 * 获取displayName
	 * 
	 * @return
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * 设置displayName
	 * 
	 * @param displayName
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
}
