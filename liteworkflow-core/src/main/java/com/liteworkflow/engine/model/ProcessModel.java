package com.liteworkflow.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义process元素
 * 
 * @version
 */
public class ProcessModel extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9000210138346445915L;

	/**
	 * 节点元素集合
	 */
	private List<NodeModel> nodeModels = new ArrayList<NodeModel>();

	/**
	 * 任务元素集合
	 */
	private List<TaskModel> taskModels = new ArrayList<TaskModel>();

	/**
	 * 流程分类
	 */
	private String category;

	/**
	 * 流程实例启动url
	 */
	private String instanceUrl;

	/**
	 * 期望完成时间
	 */
	private String expireTime;

	/**
	 * 监听器模型
	 */
	private List<ListenerModel> listenerModels;

	/**
	 * 获取所有的有序任务模型集合
	 * 
	 * @return List<TaskModel> 任务模型集合
	 */
	public synchronized List<TaskModel> getTaskModels()
	{
		if (taskModels.isEmpty())
		{
			buildModels(taskModels, getStartModel().getNextModels(TaskModel.class), TaskModel.class);
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
				buildModels(models, ((NodeModel) nextModel).getNextModels(clazz), clazz);
			}
		}
	}

	/**
	 * 获取process定义的start节点模型
	 * 
	 * @return
	 */
	public StartModel getStartModel()
	{
		for (NodeModel nodeModel : nodeModels)
		{
			if (nodeModel instanceof StartModel startModel)
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
	public EndModel getEndModel()
	{
		for (NodeModel nodeModel : nodeModels)
		{
			if (nodeModel instanceof EndModel endModel)
			{
				return endModel;
			}
		}

		return null;
	}

	/**
	 * 获取process定义的指定节点名称的节点模型
	 * 
	 * @param nodeName 节点名称
	 * @return
	 */
	public NodeModel getNodeModel(String nodeName)
	{
		for (NodeModel nodeModel : nodeModels)
		{
			if (nodeModel.getName().equals(nodeName))
			{
				return nodeModel;
			}
		}

		return null;
	}

	/**
	 * 判断当前模型的节点是否包含给定的节点名称参数
	 * 
	 * @param nodeNames 节点名称数组
	 * @return
	 */
	public <T> boolean containsNodeNames(Class<T> T, String... nodeNames)
	{
		for (NodeModel node : nodeModels)
		{
			if (!T.isInstance(node))
			{
				continue;
			}

			for (String nodeName : nodeNames)
			{
				if (node.getName().equals(nodeName))
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
	public List<NodeModel> getNodeModels()
	{
		return nodeModels;
	}

	/**
	 * 设置nodeModels
	 * 
	 * @param nodeModels
	 */
	public void setNodeModels(List<NodeModel> nodeModels)
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
	public void setTaskModels(List<TaskModel> taskModels)
	{
		this.taskModels = taskModels;
	}

	/**
	 * 获取listenerModels
	 * 
	 * @return
	 */
	public List<ListenerModel> getListenerModels()
	{
		return listenerModels;
	}

	/**
	 * 设置listenerModels
	 * 
	 * @param listenerModels
	 */
	public void setListenerModels(List<ListenerModel> listenerModels)
	{
		this.listenerModels = listenerModels;
	}
}
