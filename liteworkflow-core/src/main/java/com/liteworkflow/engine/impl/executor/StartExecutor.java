package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;

/**
 * 开始事件流程执行器
 *
 * @version
 */
public class StartExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, FlowNode nodeModel)
	{
		SequenceFlowModel sequenceFlow = getExecutableSequenceFlow(execution, nodeModel);
		if (null == sequenceFlow)
		{
			throw new WorkFlowException("The SequenceFlow cannot be matched.");
		}

		FlowExecutor executor = FlowExecutorFactory.build(sequenceFlow);
		executor.execute(execution, sequenceFlow);
	}
}
