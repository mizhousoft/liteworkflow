package com.mizhousoft.liteworkflow.engine.impl.bpmn;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ActivityBehavior;

/**
 * 活动行为工厂类
 *
 */
public interface ActivityBehaviorFactory
{
	/**
	 * 构建活动行为
	 * 
	 * @param model
	 * @return
	 */
	ActivityBehavior build(FlowElement model);
}
