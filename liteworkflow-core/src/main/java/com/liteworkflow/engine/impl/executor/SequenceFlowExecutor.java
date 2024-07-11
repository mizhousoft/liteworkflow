package com.liteworkflow.engine.impl.executor;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
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
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Execution execution, FlowElement model)
	{
		SequenceFlowModel sequenceFlow = (SequenceFlowModel) model;

		FlowNode targetModel = sequenceFlow.getTargetNode();

		// 如果目标节点模型为其它控制类型，则继续由目标节点执行
		FlowExecutor executor = FlowExecutorFactory.build(targetModel);
		executor.execute(execution, targetModel);
	}
}
