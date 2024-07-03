package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.interceptor.FlowInterceptor;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TransitionModel;

/**
 * 节点流程执行器
 *
 * @version
 */
public abstract class NodeFlowExecutor implements FlowExecutor
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
	 * 拦截
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

	/**
	 * 执行节点迁移
	 * 
	 * @param execution
	 * @param nodeModel
	 */
	protected void runOutTransition(Execution execution, NodeModel nodeModel)
	{
		List<TransitionModel> outputs = nodeModel.getOutputs();
		for (TransitionModel transition : outputs)
		{
			transition.setEnabled(true);

			FlowExecutor executor = FlowExecutorFactory.build(transition);
			executor.execute(execution, transition);
		}
	}

	/**
	 * 具体节点模型需要完成的执行逻辑
	 * 
	 * @param execution
	 * @param nodeModel
	 */
	protected abstract void doExecute(Execution execution, NodeModel nodeModel);
}
