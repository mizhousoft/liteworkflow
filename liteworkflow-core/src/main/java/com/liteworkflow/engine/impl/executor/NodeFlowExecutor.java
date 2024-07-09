package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

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
	public void execute(Execution execution, FlowElement model)
	{
		FlowNode nodeModel = (FlowNode) model;

		doExecute(execution, nodeModel);
	}

	/**
	 * 执行节点迁移
	 * 
	 * @param execution
	 * @param nodeModel
	 */
	protected void runOutTransition(Execution execution, FlowNode nodeModel)
	{
		List<SequenceFlowModel> outputs = nodeModel.getOutgoingFlows();
		for (SequenceFlowModel transition : outputs)
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
	protected abstract void doExecute(Execution execution, FlowNode nodeModel);
}
