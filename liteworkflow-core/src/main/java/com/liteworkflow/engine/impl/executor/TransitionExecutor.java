package com.liteworkflow.engine.impl.executor;

import java.util.List;

import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.impl.ServiceContext;
import com.liteworkflow.engine.interceptor.FlowInterceptor;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * TODO
 *
 * @version
 */
public class TransitionExecutor implements Executor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Execution execution, BaseModel model)
	{
		TransitionModel transitionModel = (TransitionModel) model;

		boolean enabled = transitionModel.isEnabled();
		if (!enabled)
		{
			return;
		}

		NodeModel target = transitionModel.getTarget();
		if (target instanceof TaskModel taskModel)
		{
			createTask(execution, taskModel);
		}
		else if (target instanceof SubProcessModel subProcessModel)
		{
			startSubProcess(execution, subProcessModel);
		}
		else
		{
			// 如果目标节点模型为其它控制类型，则继续由目标节点执行
			Executor executor = ExecutorBuilder.build(target);
			executor.execute(execution, target);
		}
	}

	private void createTask(Execution execution, TaskModel taskModel)
	{
		List<Task> tasks = execution.getEngineConfiguration().getTaskService().createTask(taskModel, execution);
		execution.addTasks(tasks);

		/**
		 * 从服务上下文中查找任务拦截器列表，依次对task集合进行拦截处理
		 */
		List<FlowInterceptor> interceptors = ServiceContext.getContext().findList(FlowInterceptor.class);

		try
		{
			for (FlowInterceptor interceptor : interceptors)
			{
				interceptor.intercept(execution);
			}
		}
		catch (Exception e)
		{
			throw new ProcessException(e);
		}
	}

	private void startSubProcess(Execution execution, SubProcessModel subProcessModel)
	{
		// 根据子流程模型名称获取子流程定义对象
		ProcessEngineConfiguration engineConfiguration = execution.getEngineConfiguration();
		ProcessDefinition processDefinition = engineConfiguration.getRepositoryService().getByVersion(subProcessModel.getProcessName(),
		        subProcessModel.getVersion());

		Execution child = execution.createSubExecution(execution, processDefinition, subProcessModel.getName());
		ProcessInstance instance = engineConfiguration.getRuntimeService().startInstanceByExecution(child);

		Assert.notNull(instance, "子流程创建失败");

		execution.addTasks(engineConfiguration.getTaskService().getActiveTasks(instance.getId()));
	}
}
