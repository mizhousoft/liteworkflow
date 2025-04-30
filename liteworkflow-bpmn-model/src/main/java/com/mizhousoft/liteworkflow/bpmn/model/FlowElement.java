package com.mizhousoft.liteworkflow.bpmn.model;

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
	protected String id;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 文档描述
	 */
	protected String documentation;

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

	/**
	 * 获取documentation
	 * 
	 * @return
	 */
	public String getDocumentation()
	{
		return documentation;
	}

	/**
	 * 设置documentation
	 * 
	 * @param documentation
	 */
	public void setDocumentation(String documentation)
	{
		this.documentation = documentation;
	}
}
