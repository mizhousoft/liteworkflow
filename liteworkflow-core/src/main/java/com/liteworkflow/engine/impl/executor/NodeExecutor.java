package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.interceptor.FlowInterceptor;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TransitionModel;

/**
 * TODO
 *
 * @version
 */
public abstract class NodeExecutor implements Executor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Execution execution, BaseModel model)
	{
		NodeModel nodeModel = (NodeModel) model;

		List<FlowInterceptor> preInterceptorList = nodeModel.getPreInterceptorList();
		intercept(preInterceptorList, execution);

		doExecute(execution, nodeModel);

		List<FlowInterceptor> postInterceptorList = nodeModel.getPostInterceptorList();
		intercept(postInterceptorList, execution);
	}

	/**
	 * 拦截方法
	 * 
	 * @param interceptorList 拦截器列表
	 * @param execution 执行对象
	 */
	private void intercept(List<FlowInterceptor> interceptorList, Execution execution)
	{
		try
		{
			for (FlowInterceptor interceptor : interceptorList)
			{
				interceptor.intercept(execution);
			}
		}
		catch (Exception e)
		{
			throw new ProcessException(e);
		}
	}

	protected void runOutTransition(Execution execution, NodeModel nodeModel)
	{
		List<TransitionModel> outputs = nodeModel.getOutputs();
		for (TransitionModel transition : outputs)
		{
			transition.setEnabled(true);

			Executor executor = ExecutorBuilder.build(transition);
			executor.execute(execution, transition);
		}
	}

	/**
	 * 具体节点模型需要完成的执行逻辑
	 * 
	 * @param execution 执行对象
	 */
	protected abstract void doExecute(Execution execution, NodeModel nodeModel);
}
