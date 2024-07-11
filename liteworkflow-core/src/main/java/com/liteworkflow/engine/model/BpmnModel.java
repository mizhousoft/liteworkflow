package com.liteworkflow.engine.model;

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
	 * 获取process定义的指定节点名称的节点模型
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
	 * 判断当前模型的节点是否包含给定的节点名称参数
	 * 
	 * @param nodeIds 节点Id数组
	 * @return
	 */
	public <T> boolean containsNodeIds(Class<T> T, String... nodeIds)
	{
		for (FlowElement flowElement : flowElements)
		{
			if (!T.isInstance(flowElement))
			{
				continue;
			}

			for (String nodeId : nodeIds)
			{
				if (flowElement.getId().equals(nodeId))
				{
					return true;
				}
			}
		}

		return false;
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
}
