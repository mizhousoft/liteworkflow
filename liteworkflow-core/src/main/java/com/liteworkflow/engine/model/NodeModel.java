package com.liteworkflow.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.liteworkflow.engine.interceptor.FlowInterceptor;

/**
 * 节点元素（存在输入输出的变迁）
 * 
 * @version
 */
public abstract class NodeModel extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2377317472320109317L;

	/**
	 * 输入变迁集合
	 */
	private List<TransitionModel> inputs = new ArrayList<TransitionModel>(0);

	/**
	 * 输出变迁集合
	 */
	private List<TransitionModel> outputs = new ArrayList<TransitionModel>(0);

	/**
	 * layout
	 */
	private String layout;

	/**
	 * 局部前置拦截器
	 */
	private String preInterceptors;

	/**
	 * 局部后置拦截器
	 */
	private String postInterceptors;

	/**
	 * 前置局部拦截器实例集合
	 */
	private List<FlowInterceptor> preInterceptorList = new ArrayList<FlowInterceptor>();

	/**
	 * 后置局部拦截器实例集合
	 */
	private List<FlowInterceptor> postInterceptorList = new ArrayList<FlowInterceptor>();

	/**
	 * 根据父节点模型、当前节点模型判断是否可退回。可退回条件：
	 * 1、满足中间无fork、join、subprocess模型
	 * 2、满足父节点模型如果为任务模型时，参与类型为any
	 * 
	 * @param parent 父节点模型
	 * @return 是否可以退回
	 */
	public static boolean canRejected(NodeModel current, NodeModel parent)
	{
		if (parent instanceof TaskModel && !((TaskModel) parent).isPerformAny())
		{
			return false;
		}
		boolean result = false;
		for (TransitionModel tm : current.getInputs())
		{
			NodeModel source = tm.getSource();
			if (source == parent)
			{
				return true;
			}
			if (source instanceof ForkModel || source instanceof JoinModel || source instanceof SubProcessModel
			        || source instanceof StartModel)
			{
				continue;
			}
			result = result || canRejected(source, parent);
		}
		return result;
	}

	public <T> List<T> getNextModels(Class<T> clazz)
	{
		List<T> models = new ArrayList<T>();
		for (TransitionModel tm : this.getOutputs())
		{
			addNextModels(models, tm, clazz);
		}
		return models;
	}

	protected <T> void addNextModels(List<T> models, TransitionModel tm, Class<T> clazz)
	{
		if (clazz.isInstance(tm.getTarget()))
		{
			models.add((T) tm.getTarget());
		}
		else
		{
			for (TransitionModel tm2 : tm.getTarget().getOutputs())
			{
				addNextModels(models, tm2, clazz);
			}
		}
	}

	/**
	 * 获取inputs
	 * 
	 * @return
	 */
	public List<TransitionModel> getInputs()
	{
		return inputs;
	}

	/**
	 * 设置inputs
	 * 
	 * @param inputs
	 */
	public void setInputs(List<TransitionModel> inputs)
	{
		this.inputs = inputs;
	}

	/**
	 * 获取outputs
	 * 
	 * @return
	 */
	public List<TransitionModel> getOutputs()
	{
		return outputs;
	}

	/**
	 * 设置outputs
	 * 
	 * @param outputs
	 */
	public void setOutputs(List<TransitionModel> outputs)
	{
		this.outputs = outputs;
	}

	/**
	 * 获取layout
	 * 
	 * @return
	 */
	public String getLayout()
	{
		return layout;
	}

	/**
	 * 设置layout
	 * 
	 * @param layout
	 */
	public void setLayout(String layout)
	{
		this.layout = layout;
	}

	/**
	 * 获取preInterceptors
	 * 
	 * @return
	 */
	public String getPreInterceptors()
	{
		return preInterceptors;
	}

	/**
	 * 设置preInterceptors
	 * 
	 * @param preInterceptors
	 */
	public void setPreInterceptors(String preInterceptors)
	{
		this.preInterceptors = preInterceptors;
	}

	/**
	 * 获取postInterceptors
	 * 
	 * @return
	 */
	public String getPostInterceptors()
	{
		return postInterceptors;
	}

	/**
	 * 设置postInterceptors
	 * 
	 * @param postInterceptors
	 */
	public void setPostInterceptors(String postInterceptors)
	{
		this.postInterceptors = postInterceptors;
	}

	/**
	 * 获取preInterceptorList
	 * 
	 * @return
	 */
	public List<FlowInterceptor> getPreInterceptorList()
	{
		return preInterceptorList;
	}

	/**
	 * 设置preInterceptorList
	 * 
	 * @param preInterceptorList
	 */
	public void setPreInterceptorList(List<FlowInterceptor> preInterceptorList)
	{
		this.preInterceptorList = preInterceptorList;
	}

	/**
	 * 获取postInterceptorList
	 * 
	 * @return
	 */
	public List<FlowInterceptor> getPostInterceptorList()
	{
		return postInterceptorList;
	}

	/**
	 * 设置postInterceptorList
	 * 
	 * @param postInterceptorList
	 */
	public void setPostInterceptorList(List<FlowInterceptor> postInterceptorList)
	{
		this.postInterceptorList = postInterceptorList;
	}

}
