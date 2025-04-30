package com.mizhousoft.liteworkflow.engine.impl.delegate;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;

/**
 * 监听器执行
 *
 * @version
 */
public interface ListenerInvocation
{
	/**
	 * 执行实例监听器
	 * 
	 * @param bpmnModel
	 * @param processInstance
	 * @param eventType
	 */
	void executeExecutionListeners(BpmnModel bpmnModel, ProcessInstanceEntity processInstance, String eventType);

	/**
	 * 执行任务监听器
	 * 
	 * @param taskModel
	 * @param task
	 * @param eventType
	 */
	void executeTaskListeners(UserTaskModel taskModel, TaskEntity task, String eventType);
}
