package com.liteworkflow.engine.model;

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
	 * 节点元素集合
	 */
	private List<FlowNode> nodeModels = new ArrayList<FlowNode>();

	/**
	 * 任务元素集合
	 */
	private List<UserTaskModel> taskModels = new ArrayList<UserTaskModel>();

	/**
	 * 流程分类
	 */
	private String category;

	/**
	 * 期望完成时间
	 */
	private String expireTime;

	/**
	 * 监听器元素
	 */
	private List<EventListenerElement> eventListeners;

	/**
	 * 获取所有的有序任务模型集合
	 * 
	 * @return List<TaskModel> 任务模型集合
	 */
	public synchronized List<UserTaskModel> getTaskModels()
	{
		if (taskModels.isEmpty())
		{
			buildModels(taskModels, getStartModel().getNextModels(UserTaskModel.class), UserTaskModel.class);
		}

		return taskModels;
	}

	/**
	 * 根据指定的节点类型返回流程定义中所有模型对象
	 * 
	 * @param clazz 节点类型
	 * @param <T> 泛型
	 * @return 节点列表
	 */
	public <T> List<T> getModels(Class<T> clazz)
	{
		List<T> models = new ArrayList<T>();
		buildModels(models, getStartModel().getNextModels(clazz), clazz);
		return models;
	}

	private <T> void buildModels(List<T> models, List<T> nextModels, Class<T> clazz)
	{
		for (T nextModel : nextModels)
		{
			if (!models.contains(nextModel))
			{
				models.add(nextModel);
				buildModels(models, ((FlowNode) nextModel).getNextModels(clazz), clazz);
			}
		}
	}

	/**
	 * 获取process定义的start节点模型
	 * 
	 * @return
	 */
	public StartEventModel getStartModel()
	{
		for (FlowNode nodeModel : nodeModels)
		{
			if (nodeModel instanceof StartEventModel startModel)
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
		for (FlowNode nodeModel : nodeModels)
		{
			if (nodeModel instanceof EndEventModel endModel)
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
	public FlowNode getNodeModel(String nodeId)
	{
		for (FlowNode nodeModel : nodeModels)
		{
			if (nodeModel.getId().equals(nodeId))
			{
				return nodeModel;
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
	public <T> boolean containsNodeNames(Class<T> T, String... nodeIds)
	{
		for (FlowNode node : nodeModels)
		{
			if (!T.isInstance(node))
			{
				continue;
			}

			for (String nodeId : nodeIds)
			{
				if (node.getId().equals(nodeId))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 获取nodeModels
	 * 
	 * @return
	 */
	public List<FlowNode> getNodeModels()
	{
		return nodeModels;
	}

	/**
	 * 设置nodeModels
	 * 
	 * @param nodeModels
	 */
	public void setNodeModels(List<FlowNode> nodeModels)
	{
		this.nodeModels = nodeModels;
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
	 * 获取expireTime
	 * 
	 * @return
	 */
	public String getExpireTime()
	{
		return expireTime;
	}

	/**
	 * 设置expireTime
	 * 
	 * @param expireTime
	 */
	public void setExpireTime(String expireTime)
	{
		this.expireTime = expireTime;
	}

	/**
	 * 设置taskModels
	 * 
	 * @param taskModels
	 */
	public void setTaskModels(List<UserTaskModel> taskModels)
	{
		this.taskModels = taskModels;
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
