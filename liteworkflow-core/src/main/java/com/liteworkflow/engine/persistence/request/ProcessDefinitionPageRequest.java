package com.liteworkflow.engine.persistence.request;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

/**
 * 分页请求
 *
 * @version
 */
public class ProcessDefinitionPageRequest extends PageRequest
{
	private static final long serialVersionUID = -4195498843400032234L;

	/**
	 * 流程定义Key
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 流程定义版本号
	 */
	private Integer version;

	/**
	 * 流程分类
	 */
	private String category;

	/**
	 * 构造函数
	 *
	 */
	public ProcessDefinitionPageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.ASC, "name"));
	}

	/**
	 * 获取key
	 * 
	 * @return
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * 设置key
	 * 
	 * @param key
	 */
	public void setKey(String key)
	{
		this.key = key;
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
	 * 获取version
	 * 
	 * @return
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * 设置version
	 * 
	 * @param version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * 获取category
	 * 
	 * @return
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * 设置category
	 * 
	 * @param category
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}
}
