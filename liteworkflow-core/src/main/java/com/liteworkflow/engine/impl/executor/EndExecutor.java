package com.liteworkflow.engine.impl.executor;

import java.util.List;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * TODO
 *
 * @version
 */
public class EndExecutor extends NodeExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		ProcessEngineConfiguration engine = execution.getEngineConfiguration();
		ProcessInstance instance = execution.getInstance();
		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			if (task.isMajor())
			{
				throw new ProcessException("存在未完成的主办任务,请确认.");
			}

			engine.getTaskService().complete(task.getId(), ProcessEngine.AUTO);
		}
		/**
		 * 结束当前流程实例
		 */
		engine.getProcessInstanceService().complete(instance.getId());

		/**
		 * 如果存在父流程，则重新构造Execution执行对象，交给父流程的SubProcessModel模型execute
		 */
		if (StringHelper.isNotEmpty(instance.getParentId()))
		{
			ProcessInstance parentInstance = engine.getProcessInstanceService().getInstance(instance.getParentId());
			if (parentInstance == null)
			{
				return;
			}

			ProcessDefinition process = engine.getRepositoryService().getProcessById(parentInstance.getProcessId());
			ProcessModel pm = process.getModel();
			if (pm == null)
			{
				return;
			}

			SubProcessModel spm = (SubProcessModel) pm.getNode(instance.getParentNodeName());
			Execution newExecution = new Execution(engine, process, parentInstance, execution.getArgs());
			newExecution.setChildOrderId(instance.getId());
			newExecution.setTask(execution.getTask());

			Executor executor = ExecutorBuilder.build(spm);
			executor.execute(newExecution, spm);

			/**
			 * SubProcessModel执行结果的tasks合并到当前执行对象execution的tasks列表中
			 */
			execution.addTasks(newExecution.getTasks());
		}
	}
}
