package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Expression;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.el.SpelExpression;
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
	 * 表达式解析器
	 */
	private Expression expression = new SpelExpression();

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
	 * 具体节点模型需要完成的执行逻辑
	 * 
	 * @param execution
	 * @param nodeModel
	 */
	protected abstract void doExecute(Execution execution, FlowNode nodeModel);

	/**
	 * 执行节点迁移
	 * 
	 * @param execution
	 * @param nodeModel
	 */
	protected void runOutTransition(Execution execution, FlowNode nodeModel)
	{
		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			FlowExecutor executor = FlowExecutorFactory.build(outgoingFlow);
			executor.execute(execution, outgoingFlow);
		}
	}

	/**
	 * 获取可执行的顺序流
	 * 
	 * @param execution
	 * @param nodeModel
	 * @return
	 */
	protected SequenceFlowModel getExecutableSequenceFlow(Execution execution, FlowNode nodeModel)
	{
		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
		List<SequenceFlowModel> conditionFlows = outgoingFlows.stream().filter(of -> null != of.getConditionExpression()).toList();

		for (SequenceFlowModel conditionFlow : conditionFlows)
		{
			if (canSequenceFlowExecute(execution, conditionFlow))
			{
				return conditionFlow;
			}
		}

		SequenceFlowModel defaultFlow = outgoingFlows.stream().filter(of -> null == of.getConditionExpression()).findFirst().orElse(null);

		return defaultFlow;
	}

	/**
	 * 顺序流是否能执行
	 * 
	 * @param execution
	 * @param sequenceFlow
	 * @return
	 */
	protected boolean canSequenceFlowExecute(Execution execution, SequenceFlowModel sequenceFlow)
	{
		String expr = sequenceFlow.getConditionExpression();
		if (null == expr)
		{
			return true;
		}

		if (expression.eval(Boolean.class, expr, execution.getArgs()))
		{
			return true;
		}

		return false;
	}
}
