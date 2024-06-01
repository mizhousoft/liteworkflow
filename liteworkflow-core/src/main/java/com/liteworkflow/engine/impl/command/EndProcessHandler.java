package com.liteworkflow.engine.impl.command;

import java.util.List;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.IHandler;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.process.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.task.entity.Task;

/**
 * 结束流程实例的处理器
 * 
 * @author yuqs
 * @since 1.0
 */
public class EndProcessHandler implements IHandler
{
	/**
	 * 结束当前流程实例，如果存在父流程，则触发父流程继续执行
	 */
	public void handle(Execution execution)
	{
		ProcessEngineConfiguration engine = execution.getEngineConfiguration();
		ProcessInstance order = execution.getOrder();
		List<Task> tasks = engine.getTaskService().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			if (task.isMajor())
				throw new ProcessException("存在未完成的主办任务,请确认.");
			engine.getTaskService().complete(task.getId(), ProcessEngine.AUTO);
		}
		/**
		 * 结束当前流程实例
		 */
		engine.getProcessInstanceService().complete(order.getId());

		/**
		 * 如果存在父流程，则重新构造Execution执行对象，交给父流程的SubProcessModel模型execute
		 */
		if (StringHelper.isNotEmpty(order.getParentId()))
		{
			ProcessInstance parentOrder = engine.getProcessInstanceService().getOrder(order.getParentId());
			if (parentOrder == null)
				return;
			ProcessDefinition process = engine.getRepositoryService().getProcessById(parentOrder.getProcessId());
			ProcessModel pm = process.getModel();
			if (pm == null)
				return;
			SubProcessModel spm = (SubProcessModel) pm.getNode(order.getParentNodeName());
			Execution newExecution = new Execution(engine, process, parentOrder, execution.getArgs());
			newExecution.setChildOrderId(order.getId());
			newExecution.setTask(execution.getTask());
			spm.execute(newExecution);
			/**
			 * SubProcessModel执行结果的tasks合并到当前执行对象execution的tasks列表中
			 */
			execution.addTasks(newExecution.getTasks());
		}
	}
}
