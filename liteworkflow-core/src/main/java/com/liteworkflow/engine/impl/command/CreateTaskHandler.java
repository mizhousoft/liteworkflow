package com.liteworkflow.engine.impl.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.IHandler;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.ServiceContext;
import com.liteworkflow.engine.interceptor.SnakerInterceptor;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.task.entity.Task;

/**
 * 任务创建操作的处理器
 * 
 * @author yuqs
 * @since 1.0
 */
public class CreateTaskHandler implements IHandler
{
	private static final Logger log = LoggerFactory.getLogger(CreateTaskHandler.class);

	/**
	 * 任务模型
	 */
	private TaskModel model;

	/**
	 * 调用者需要提供任务模型
	 * 
	 * @param model 模型
	 */
	public CreateTaskHandler(TaskModel model)
	{
		this.model = model;
	}

	/**
	 * 根据任务模型、执行对象，创建下一个任务，并添加到execution对象的tasks集合中
	 */
	public void handle(Execution execution)
	{
		List<Task> tasks = execution.getEngineConfiguration().getTaskService().createTask(model, execution);
		execution.addTasks(tasks);
		/**
		 * 从服务上下文中查找任务拦截器列表，依次对task集合进行拦截处理
		 */
		List<SnakerInterceptor> interceptors = ServiceContext.getContext().findList(SnakerInterceptor.class);
		try
		{
			for (SnakerInterceptor interceptor : interceptors)
			{
				interceptor.intercept(execution);
			}
		}
		catch (Exception e)
		{
			throw new ProcessException(e);
		}
	}
}
