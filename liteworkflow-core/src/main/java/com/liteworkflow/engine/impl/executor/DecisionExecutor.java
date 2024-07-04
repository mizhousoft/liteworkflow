package com.liteworkflow.engine.impl.executor;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.DecisionHandler;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.Expression;
import com.liteworkflow.engine.impl.el.SpelExpression;
import com.liteworkflow.engine.model.DecisionModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TransitionModel;

/**
 * TODO
 *
 * @version
 */
public class DecisionExecutor extends NodeFlowExecutor
{
	private static final Logger log = LoggerFactory.getLogger(DecisionExecutor.class);

	/**
	 * 表达式解析器
	 */
	private Expression expression = new SpelExpression();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		log.info(execution.getProcessInstance().getId() + "->decision execution.getArgs():" + execution.getArgs());

		DecisionModel decisionModel = (DecisionModel) nodeModel;
		DecisionHandler decideHandler = decisionModel.getDecisionHandler();

		String next = null;
		if (!StringUtils.isBlank(decisionModel.getExpr()))
		{
			next = expression.eval(String.class, decisionModel.getExpr(), execution.getArgs());
		}
		else if (decideHandler != null)
		{
			next = decideHandler.decide(execution);
		}

		log.info(execution.getProcessInstance().getId() + "->decision expression[expr=" + decisionModel.getExpr() + "] return result:"
		        + next);
		boolean isfound = false;

		List<TransitionModel> outputs = nodeModel.getOutputs();
		for (TransitionModel tm : outputs)
		{
			if (StringUtils.isBlank(next))
			{
				String expr = tm.getExpr();
				if (!StringUtils.isBlank(expr) && expression.eval(Boolean.class, expr, execution.getArgs()))
				{
					tm.setEnabled(true);

					FlowExecutor executor = FlowExecutorFactory.build(tm);
					executor.execute(execution, tm);

					isfound = true;
				}
			}
			else
			{
				if (tm.getName().equals(next))
				{
					tm.setEnabled(true);
					FlowExecutor executor = FlowExecutorFactory.build(tm);
					executor.execute(execution, tm);
					isfound = true;
				}
			}
		}

		if (!isfound)
		{
			throw new WorkFlowException(execution.getProcessInstance().getId() + "->decision节点无法确定下一步执行路线");
		}
	}

}
