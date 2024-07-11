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
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.model.UserTaskModel.PerformType;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.TaskEntityService;

/**
 * TODO
 *
 * @version
 */
public class UserTaskExecutor extends NodeFlowExecutor
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, FlowNode nodeModel)
	{
		UserTaskModel taskModel = (UserTaskModel) nodeModel;

		createTask(taskModel, execution);
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
		task.setName(model.getName());
		task.setCreateTime(LocalDateTime.now());
		task.setTaskType(0);
		task.setParentTaskId(execution.getTask() == null ? null : execution.getTask().getId());
		return task;
	}
}
