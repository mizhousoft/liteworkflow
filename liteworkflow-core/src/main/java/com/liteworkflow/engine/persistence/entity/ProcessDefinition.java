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
	 * 是否可用的开关
	 */
	private Integer state;

	/**
	 * 版本
	 */
	private Integer version;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

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

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ProcessModel getModel()
	{
		return model;
	}

	/**
	 * setter name/displayName/instanceUrl
	 * 
	 * @param processModel
	 */
	public void setModel(ProcessModel processModel)
	{
		this.model = processModel;
		this.name = processModel.getName();
		this.category = processModel.getCategory();
		this.displayName = processModel.getDisplayName();
		this.instanceUrl = processModel.getInstanceUrl();
	}

	public String getInstanceUrl()
	{
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl)
	{
		this.instanceUrl = instanceUrl;
	}

	public byte[] getBytes()
	{
		return bytes;
	}

	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Process(id=").append(this.id);
		sb.append(",name=").append(this.name);
		sb.append(",displayName=").append(this.displayName);
		sb.append(",version=").append(this.version);
		sb.append(",state=").append(this.state).append(")");
		return sb.toString();
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

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}
}
