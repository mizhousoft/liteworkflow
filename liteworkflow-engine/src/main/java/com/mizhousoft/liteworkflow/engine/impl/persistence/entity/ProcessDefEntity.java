package com.mizhousoft.liteworkflow.engine.impl.persistence.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.domain.ProcessDefinition;

/**
 * 流程定义实体类
 * 
 * @version
 */
public class ProcessDefEntity implements ProcessDefinition
{
	/**
	 * 主键ID
	 */
	private int id;

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
	@JsonIgnore
	private byte[] bytes;

	/**
	 * 流程描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updateTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 流程定义模型
	 */
	private BpmnModel bpmnModel;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	@Override
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 获取key
	 * 
	 * @return
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	 * 获取description
	 * 
	 * @return
	 */
	@Override
	public String getDescription()
	{
		return description;
	}

	/**
	 * 设置description
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 获取createTime
	 * 
	 * @return
	 */
	@Override
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
	 * 获取updateTime
	 * 
	 * @return
	 */
	@Override
	public LocalDateTime getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * 设置updateTime
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(LocalDateTime updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * 获取creator
	 * 
	 * @return
	 */
	@Override
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
