package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import org.springframework.context.ApplicationContext;

import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.Expression;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.el.SpelExpression;

/**
 * 节点流程执行器
 *
 * @version
 */
public abstract class NodeActivityBehavior implements ActivityBehavior
{
	/**
	 * 顺序流是否能执行
	 * 
	 * @param execution
	 * @param sequenceFlow
	 * @return
	 */
	protected boolean canNextFlowExecute(Execution execution, SequenceFlowModel sequenceFlow)
	{
		String expr = sequenceFlow.getConditionExpression();
		if (null == expr)
		{
			return true;
		}

		ApplicationContext applicationContext = execution.getEngineConfiguration().getApplicationContext();
		Expression expression = new SpelExpression(applicationContext);

		if (expression.eval(Boolean.class, expr, execution.getArgs()))
		{
			return true;
		}

		return false;
	}
}
