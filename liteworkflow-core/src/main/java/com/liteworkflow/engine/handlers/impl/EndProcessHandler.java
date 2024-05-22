package com.liteworkflow.engine.handlers.impl;

import java.util.List;

import com.liteworkflow.WorkflowException;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.core.Execution;
import com.liteworkflow.engine.handlers.IHandler;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.process.entity.Process;
import com.liteworkflow.task.entity.Task;

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
		ProcessEngine engine = execution.getEngine();
		Order order = execution.getOrder();
		List<Task> tasks = engine.query().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			if (task.isMajor())
				throw new WorkflowException("存在未完成的主办任务,请确认.");
			engine.task().complete(task.getId(), ProcessEngine.AUTO);
		}
		/**
		 * 结束当前流程实例
		 */
		engine.order().complete(order.getId());

		/**
		 * 如果存在父流程，则重新构造Execution执行对象，交给父流程的SubProcessModel模型execute
		 */
		if (StringHelper.isNotEmpty(order.getParentId()))
		{
			Order parentOrder = engine.query().getOrder(order.getParentId());
			if (parentOrder == null)
				return;
			Process process = engine.process().getProcessById(parentOrder.getProcessId());
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
