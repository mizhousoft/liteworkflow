package com.liteworkflow.engine.persistence.entity;

import java.time.LocalDateTime;

import com.liteworkflow.engine.model.BpmnModel;

/**
 * 流程定义实体类
 * 
 * @version
 */
public class ProcessDefinition
{
	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 流程定义Key
	 */
	private String key;

	/**
	 * 流程定义名称
	 */
	private String name;

	/**
	 * 流程定义分类
	 */
	private String category;

	/**
	 * 版本
	 */
	private int version;

	/**
	 * 流程定义字节数组
	 */
	private byte[] bytes;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 流程定义模型
	 */
	private BpmnModel bpmnModel;

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

	/**
	 * 获取version
	 * 
	 * @return
	 */
	public int getVersion()
	{
		return version;
	}

	/**
	 * 设置version
	 * 
	 * @param version
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}

	/**
	 * 获取bytes
	 * 
	 * @return
	 */
	public byte[] getBytes()
	{
		return bytes;
	}

	/**
	 * 设置bytes
	 * 
	 * @param bytes
	 */
	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}

	/**
	 * 获取createTime
	 * 
	 * @return
	 */
	public LocalDateTime getCreateTime()
	{
		return createTime;
	}

	/**
	 * 设置createTime
	 * 
	 * @param createTime
	 */
	public void setCreateTime(LocalDateTime createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * 获取creator
	 * 
	 * @return
	 */
	public String getCreator()
	{
		return creator;
	}

	/**
	 * 设置creator
	 * 
	 * @param creator
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	/**
	 * 获取bpmnModel
	 * 
	 * @return
	 */
	public BpmnModel getBpmnModel()
	{
		return bpmnModel;
	}

	/**
	 * 设置bpmnModel
	 * 
	 * @param bpmnModel
	 */
	public void setBpmnModel(BpmnModel bpmnModel)
	{
		this.bpmnModel = bpmnModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"")
		        .append(id)
		        .append("\", \"key\":\"")
		        .append(key)
		        .append("\", \"name\":\"")
		        .append(name)
		        .append("\", \"category\":\"")
		        .append(category)
		        .append("\", \"version\":\"")
		        .append(version)
		        .append("\"}");
		return builder.toString();
	}
}
