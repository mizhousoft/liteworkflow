package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import org.springframework.context.ApplicationContext;

import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.Expression;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;
import com.mizhousoft.liteworkflow.engine.impl.el.SpelExpression;

/**
 * 流程迁移流程执行器
 *
 * @version
 */
public class SequenceFlowActivityBehavior implements ActivityBehavior
{
	/**
	 * 模型
	 */
	private SequenceFlowModel sequenceFlow;

	/**
	 * 构造函数
	 *
	 * @param sequenceFlow
	 */
	public SequenceFlowActivityBehavior(SequenceFlowModel sequenceFlow)
	{
		super();
		this.sequenceFlow = sequenceFlow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		if (!canNextFlowExecute(execution, sequenceFlow))
		{
			return false;
		}

		FlowNode targetModel = sequenceFlow.getTargetNode();
		ActivityBehavior executor = execution.getEngineConfiguration().getActivityBehaviorFactory().build(targetModel);
		boolean succeed = executor.execute(execution);

		return succeed;
	}

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
