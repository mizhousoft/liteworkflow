package com.liteworkflow.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.liteworkflow.engine.helper.ClassHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.interceptor.SnakerInterceptor;

/**
 * 节点元素（存在输入输出的变迁）
 * 
 * @author
 * @since 1.0
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
	private List<TransitionModel> inputs = new ArrayList<TransitionModel>();

	/**
	 * 输出变迁集合
	 */
	private List<TransitionModel> outputs = new ArrayList<TransitionModel>();

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
	private List<SnakerInterceptor> preInterceptorList = new ArrayList<SnakerInterceptor>();

	/**
	 * 后置局部拦截器实例集合
	 */
	private List<SnakerInterceptor> postInterceptorList = new ArrayList<SnakerInterceptor>();

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

	public List<TransitionModel> getInputs()
	{
		return inputs;
	}

	public void setInputs(List<TransitionModel> inputs)
	{
		this.inputs = inputs;
	}

	public List<TransitionModel> getOutputs()
	{
		return outputs;
	}

	public void setOutputs(List<TransitionModel> outputs)
	{
		this.outputs = outputs;
	}

	public String getLayout()
	{
		return layout;
	}

	public void setLayout(String layout)
	{
		this.layout = layout;
	}

	public String getPreInterceptors()
	{
		return preInterceptors;
	}

	public void setPreInterceptors(String preInterceptors)
	{
		this.preInterceptors = preInterceptors;
		if (StringHelper.isNotEmpty(preInterceptors))
		{
			for (String interceptor : preInterceptors.split(","))
			{
				SnakerInterceptor instance = (SnakerInterceptor) ClassHelper.newInstance(interceptor);
				if (instance != null)
					this.preInterceptorList.add(instance);
			}
		}
	}

	public String getPostInterceptors()
	{
		return postInterceptors;
	}

	public void setPostInterceptors(String postInterceptors)
	{
		this.postInterceptors = postInterceptors;
		if (StringHelper.isNotEmpty(postInterceptors))
		{
			for (String interceptor : postInterceptors.split(","))
			{
				SnakerInterceptor instance = (SnakerInterceptor) ClassHelper.newInstance(interceptor);
				if (instance != null)
					this.postInterceptorList.add(instance);
			}
		}
	}

	/**
	 * 获取preInterceptorList
	 * 
	 * @return
	 */
	public List<SnakerInterceptor> getPreInterceptorList()
	{
		return preInterceptorList;
	}

	/**
	 * 获取postInterceptorList
	 * 
	 * @return
	 */
	public List<SnakerInterceptor> getPostInterceptorList()
	{
		return postInterceptorList;
	}
}
