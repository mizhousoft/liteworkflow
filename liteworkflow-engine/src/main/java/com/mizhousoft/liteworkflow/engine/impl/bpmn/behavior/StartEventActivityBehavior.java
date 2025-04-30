package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import java.util.List;

import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.bpmn.model.StartEventModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;

/**
 * 开始事件流程执行器
 *
 * @version
 */
public class StartEventActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 模型
	 */
	private StartEventModel nodeModel;

	/**
	 * 构造函数
	 *
	 * @param nodeModel
	 */
	public StartEventActivityBehavior(StartEventModel nodeModel)
	{
		super();
		this.nodeModel = nodeModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		boolean succeed = false;

		List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
		for (SequenceFlowModel outgoingFlow : outgoingFlows)
		{
			ActivityBehavior flowExecutor = execution.getEngineConfiguration().getActivityBehaviorFactory().build(outgoingFlow);
			boolean result = flowExecutor.execute(execution);

			if (result)
			{
				succeed = result;
			}
		}

		if (!succeed)
		{
			throw new WorkFlowException("No matching sequential flow is executed.");
		}

		return succeed;
	}
}
