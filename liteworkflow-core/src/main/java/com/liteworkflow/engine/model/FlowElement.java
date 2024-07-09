package com.liteworkflow.engine.model;

/**
 * 流程元素基类
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
	 * 名称
	 */
	private String name;

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
}
