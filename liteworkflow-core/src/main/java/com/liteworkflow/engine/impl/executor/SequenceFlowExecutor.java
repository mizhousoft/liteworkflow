package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Expression;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.el.SpelExpression;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * 流程迁移流程执行器
 *
 * @version
 */
public class SequenceFlowExecutor implements FlowExecutor
{
	/**
	 * 表达式解析器
	 */
	private Expression expression = new SpelExpression();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution, FlowElement model)
	{
		SequenceFlowModel sequenceFlow = (SequenceFlowModel) model;

		if (!canSequenceFlowExecute(execution, sequenceFlow))
		{
			return false;
		}

		FlowNode targetModel = sequenceFlow.getTargetNode();

		// 如果目标节点模型为其它控制类型，则继续由目标节点执行
		FlowExecutor executor = FlowExecutorFactory.build(targetModel);
		executor.execute(execution, targetModel);

		return true;
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

		String actualExpr = expr.replaceFirst("\\$", "#");
		actualExpr = actualExpr.replaceFirst("\\{", "");
		actualExpr = actualExpr.replaceFirst("}", "");
		if (expression.eval(Boolean.class, actualExpr, execution.getArgs()))
		{
			return true;
		}

		return false;
	}
}
