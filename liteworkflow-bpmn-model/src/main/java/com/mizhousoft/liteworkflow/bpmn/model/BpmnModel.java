package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义模型
 * 
 * @version
 */
public class BpmnModel extends FlowElement
{
	/**
	 * 流程分类
	 */
	private String category;

	/**
	 * 元素集合
	 */
	private List<FlowElement> flowElements;

	/**
	 * 监听器元素
	 */
	private List<EventListenerElement> eventListeners;

	/**
	 * 自定义属性
	 */
	private List<CustomProperty> customProperties;

	/**
	 * 获取process定义的start节点模型
	 * 
	 * @return
	 */
	public StartEventModel getStartModel()
	{
		for (FlowElement flowElement : flowElements)
		{
			if (flowElement instanceof StartEventModel startModel)
			{
				return startModel;
			}
		}

		return null;
	}

	/**
	 * 获取结束节点模型
	 * 
	 * @return
	 */
	public EndEventModel getEndModel()
	{
		for (FlowElement flowElement : flowElements)
		{
			if (flowElement instanceof EndEventModel endModel)
			{
				return endModel;
			}
		}

		return null;
	}

	/**
	 * 获取用户任务模型
	 * 
	 * @return
	 */
	public List<UserTaskModel> getUserTasks()
	{
		List<UserTaskModel> list = new ArrayList<>(5);

		for (FlowElement flowElement : flowElements)
		{
			if (flowElement instanceof UserTaskModel taskModel)
			{
				list.add(taskModel);
			}
		}

		return list;
	}

	/**
	 * 根据节点ID获取节点模型
	 * 
	 * @param nodeId 节点ID
	 * @return
	 */
	public FlowNode getFlowNodeModel(String nodeId)
	{
		for (FlowElement flowElement : flowElements)
		{
			if (flowElement.getId().equals(nodeId) && flowElement instanceof FlowNode flowNode)
			{
				return flowNode;
			}
		}

		return null;
	}

	/**
	 * 根据节点ID获取节点模型
	 * 
	 * @param nodeId 节点ID
	 * @return
	 */
	public ActivityModel getFlowActivityModel(String nodeId)
	{
		for (FlowElement flowElement : flowElements)
		{
			if (flowElement.getId().equals(nodeId) && flowElement instanceof ActivityModel activityModel)
			{
				return activityModel;
			}
		}

		return null;
	}

	public UserTaskModel getFlowUserTaskModel(String nodeId)
	{
		for (FlowElement flowElement : flowElements)
		{
			if (flowElement.getId().equals(nodeId) && flowElement instanceof UserTaskModel userTask)
			{
				return userTask;
			}
		}

		return null;
	}

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
	 * 获取flowElements
	 * 
	 * @return
	 */
	public List<FlowElement> getFlowElements()
	{
		return flowElements;
	}

	/**
	 * 设置flowElements
	 * 
	 * @param flowElements
	 */
	public void setFlowElements(List<FlowElement> flowElements)
	{
		this.flowElements = flowElements;
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
	 * 获取eventListeners
	 * 
	 * @return
	 */
	public List<EventListenerElement> getEventListeners()
	{
		return eventListeners;
	}

	/**
	 * 设置eventListeners
	 * 
	 * @param eventListeners
	 */
	public void setEventListeners(List<EventListenerElement> eventListeners)
	{
		this.eventListeners = eventListeners;
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
}
