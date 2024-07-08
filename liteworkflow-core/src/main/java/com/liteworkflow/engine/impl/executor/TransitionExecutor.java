package com.liteworkflow.engine.impl.executor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.StartEventModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.model.UserTaskModel.PerformType;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.util.ProcessInstanceUtils;

/**
 * 流程迁移流程执行器
 *
 * @version
 */
public class TransitionExecutor implements FlowExecutor
{
	private static final String START = "start";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Execution execution, FlowElement model)
	{
		TransitionModel transitionModel = (TransitionModel) model;

		boolean enabled = transitionModel.isEnabled();
		if (!enabled)
		{
			return;
		}

		FlowNode targetModel = transitionModel.getTarget();
		if (targetModel instanceof UserTaskModel taskModel)
		{
			createTask(execution, taskModel);
		}
		else
		{
			// 如果目标节点模型为其它控制类型，则继续由目标节点执行
			FlowExecutor executor = FlowExecutorFactory.build(targetModel);
			executor.execute(execution, targetModel);
		}
	}

	private void createTask(Execution execution, UserTaskModel taskModel)
	{
		List<Task> tasks = createTask(taskModel, execution);
		execution.addTasks(tasks);
	}

	public ProcessInstance startInstanceByExecution(Execution execution)
	{
		ProcessDefinition processDefinition = execution.getProcessDefinition();
		StartEventModel startModel = processDefinition.getBpmnModel().getStartModel();

		ProcessInstance instance = ProcessInstanceUtils.createProcessInstance(processDefinition, null,
		        execution.getParentInstance().getCreator(), execution.getArgs(), execution.getParentInstance().getId(),
		        execution.getParentNodeName(), execution.getEngineConfiguration());

		Execution current = new Execution(execution.getEngineConfiguration(), processDefinition, instance, execution.getArgs());

		FlowExecutor executor = FlowExecutorFactory.build(startModel);
		executor.execute(current, startModel);

		return current.getProcessInstance();
	}

	public List<Task> createTask(UserTaskModel taskModel, Execution execution)
	{
		List<Task> tasks = new ArrayList<Task>();

		Map<String, Object> args = execution.getArgs();
		if (args == null)
			args = new HashMap<String, Object>();

		Task task = createTaskBase(taskModel, execution);

		task.setVariable(JsonHelper.toJson(args));

		if (taskModel.isPerformAny())
		{
			// 任务执行方式为参与者中任何一个执行即可驱动流程继续流转，该方法只产生一个task
			task = saveTask(execution, task);
			tasks.add(task);
		}
		else if (taskModel.isPerformAll())
		{
			// 任务执行方式为参与者中每个都要执行完才可驱动流程继续流转，该方法根据参与者个数产生对应的task数量
			Task singleTask = new Task();
			BeanUtils.copyProperties(task, singleTask);

			singleTask = saveTask(execution, singleTask);
			tasks.add(singleTask);
		}
		return tasks;
	}

	/**
	 * 由DBAccess实现类持久化task对象
	 */
	private Task saveTask(Execution execution, Task task)
	{
		TaskEntityService taskEntityService = execution.getEngineConfiguration().getTaskEntityService();

		task.setId(StringHelper.getPrimaryKey());
		task.setPerformType(PerformType.ANY.ordinal());
		taskEntityService.addEntity(task);
		return task;
	}

	/**
	 * 根据模型、执行对象、任务类型构建基本的task对象
	 * 
	 * @param model 模型
	 * @param execution 执行对象
	 * @return Task任务对象
	 */
	private Task createTaskBase(UserTaskModel model, Execution execution)
	{
		Task task = new Task();
		task.setProcessDefinitionId(execution.getProcessInstance().getProcessDefinitionId());
		task.setInstanceId(execution.getProcessInstance().getId());
		task.setTaskDefinitionId(model.getId());
		task.setDisplayName(model.getDisplayName());
		task.setCreateTime(LocalDateTime.now());
		task.setTaskType(0);
		task.setParentTaskId(execution.getTask() == null ? START : execution.getTask().getId());
		task.setModel(model);
		return task;
	}
}
