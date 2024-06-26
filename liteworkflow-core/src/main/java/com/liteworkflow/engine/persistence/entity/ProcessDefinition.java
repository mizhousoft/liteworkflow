package com.liteworkflow.engine.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.liteworkflow.engine.model.ProcessModel;

/**
 * 流程定义实体类
 * 
 * @author
 * @since 1.0
 */
public class ProcessDefinition implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6541688543201014542L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 流程定义名称
	 */
	private String name;

	/**
	 * 流程定义显示名称
	 */
	private String displayName;

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
	 * 当前流程的实例url（一般为流程第一步的url）
	 * 该字段可以直接打开流程申请的表单
	 */
	private String instanceUrl;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 流程定义模型
	 */
	private ProcessModel model;

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
	 * 获取instanceUrl
	 * 
	 * @return
	 */
	public String getInstanceUrl()
	{
		return instanceUrl;
	}

	/**
	 * 设置instanceUrl
	 * 
	 * @param instanceUrl
	 */
	public void setInstanceUrl(String instanceUrl)
	{
		this.instanceUrl = instanceUrl;
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
	 * 获取model
	 * 
	 * @return
	 */
	public ProcessModel getModel()
	{
		return model;
	}

	/**
	 * 设置model
	 * 
	 * @param model
	 */
	public void setModel(ProcessModel model)
	{
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"name\":\"")
		        .append(name)
		        .append("\", \"displayName\":\"")
		        .append(displayName)
		        .append("\", \"category\":\"")
		        .append(category)
		        .append("\", \"version\":\"")
		        .append(version)
		        .append("\"}");
		return builder.toString();
	}
}
