package com.liteworkflow.engine.model;

/**
 * 模型元素基类
 * 
 * @version
 */
public class FlowElement
{
	/**
	 * 元素ID
	 */
	private String id;

	/**
	 * 显示名称
	 */
	private String displayName;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
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
