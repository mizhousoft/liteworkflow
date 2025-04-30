package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.List;

/**
 * 活动元素
 * 
 * @version
 */
public abstract class ActivityModel extends FlowNode
{
	/**
	 * 多实例特征元素
	 */
	protected MultiInstanceLoopCharacteristics loopCharacteristics;

	/**
	 * 自定义属性
	 */
	protected List<CustomProperty> customProperties;

	/**
	 * 操作
	 */
	protected List<FlowOperation> operations;

	/**
	 * 抄送人
	 */
	protected List<FlowCC> cclist;

	/**
	 * 获取自定义属性值
	 * 
	 * @param key
	 * @return
	 */
	public String getCustomPropertyValue(String key)
	{
		CustomProperty prop = customProperties.stream().filter(item -> key.equals(item.getName())).findFirst().orElse(null);
		if (null == prop)
		{
			return null;
		}

		return prop.getValue();
	}

	/**
	 * 获取流程操作
	 * 
	 * @param id
	 * @return
	 */
	public FlowOperation getFlowOperation(String id)
	{
		return operations.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
	}

	/**
	 * 获取loopCharacteristics
	 * 
	 * @return
	 */
	public MultiInstanceLoopCharacteristics getLoopCharacteristics()
	{
		return loopCharacteristics;
	}

	/**
	 * 设置loopCharacteristics
	 * 
	 * @param loopCharacteristics
	 */
	public void setLoopCharacteristics(MultiInstanceLoopCharacteristics loopCharacteristics)
	{
		this.loopCharacteristics = loopCharacteristics;
	}

	/**
	 * 获取customProperties
	 * 
	 * @return
	 */
	public List<CustomProperty> getCustomProperties()
	{
		return customProperties;
	}

	/**
	 * 设置customProperties
	 * 
	 * @param customProperties
	 */
	public void setCustomProperties(List<CustomProperty> customProperties)
	{
		this.customProperties = customProperties;
	}

	/**
	 * 获取operations
	 * 
	 * @return
	 */
	public List<FlowOperation> getOperations()
	{
		return operations;
	}

	/**
	 * 设置operations
	 * 
	 * @param operations
	 */
	public void setOperations(List<FlowOperation> operations)
	{
		this.operations = operations;
	}

	/**
	 * 获取cclist
	 * 
	 * @return
	 */
	public List<FlowCC> getCclist()
	{
		return cclist;
	}

	/**
	 * 设置cclist
	 * 
	 * @param cclist
	 */
	public void setCclist(List<FlowCC> cclist)
	{
		this.cclist = cclist;
	}

}
