package com.liteworkflow.engine.impl.command;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.IHandler;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * 启动子流程的处理器
 * 
 * @author yuqs
 * @since 1.0
 */
public class StartSubProcessHandler implements IHandler
{
	private SubProcessModel model;

	/**
	 * 是否以future方式执行启动子流程任务
	 */
	private boolean isFutureRunning = false;

	public StartSubProcessHandler(SubProcessModel model)
	{
		this.model = model;
	}

	public StartSubProcessHandler(SubProcessModel model, boolean isFutureRunning)
	{
		this.model = model;
		this.isFutureRunning = isFutureRunning;
	}

	/**
	 * 子流程执行的处理
	 */
	public void handle(Execution execution)
	{
		// 根据子流程模型名称获取子流程定义对象
		ProcessEngineConfiguration engineConfiguration = execution.getEngineConfiguration();
		ProcessDefinition process = engineConfiguration.getRepositoryService().getProcessByVersion(model.getProcessName(),
		        model.getVersion());

		Execution child = execution.createSubExecution(execution, process, model.getName());
		ProcessInstance instance = null;
		if (isFutureRunning)
		{
			// 创建单个线程执行器来执行启动子流程的任务
			ExecutorService es = Executors.newSingleThreadExecutor();
			// 提交执行任务，并返回future
			Future<ProcessInstance> future = es.submit(new ExecuteTask(execution, process, model.getName()));
			try
			{
				es.shutdown();
				instance = future.get();
			}
			catch (InterruptedException e)
			{
				throw new ProcessException("创建子流程线程被强制终止执行", e.getCause());
			}
			catch (ExecutionException e)
			{
				throw new ProcessException("创建子流程线程执行异常.", e.getCause());
			}
		}
		else
		{
			instance = engineConfiguration.getRuntimeService().startInstanceByExecution(child);
		}
		AssertHelper.notNull(instance, "子流程创建失败");
		execution.addTasks(engineConfiguration.getTaskService().getActiveTasks(instance.getId()));
	}

	/**
	 * Future模式的任务执行。通过call返回任务结果集
	 * 
	 * @author yuqs
	 * @since 1.0
	 */
	class ExecuteTask implements Callable<ProcessInstance>
	{
		private ProcessEngineConfiguration engineConfiguration;

		private Execution child;

		/**
		 * 构造函数
		 * 
		 * @param execution
		 * @param process
		 * @param parentNodeName
		 */
		public ExecuteTask(Execution execution, ProcessDefinition process, String parentNodeName)
		{
			this.engineConfiguration = execution.getEngineConfiguration();
			child = execution.createSubExecution(execution, process, parentNodeName);
		}

		public ProcessInstance call() throws Exception
		{
			return this.engineConfiguration.getRuntimeService().startInstanceByExecution(child);
		}
	}
}
