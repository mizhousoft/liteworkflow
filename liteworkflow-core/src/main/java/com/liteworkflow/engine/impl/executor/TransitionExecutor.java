package com.liteworkflow.engine.impl.executor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Assignment;
import com.liteworkflow.engine.AssignmentHandler;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.BaseModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TaskModel.PerformType;
import com.liteworkflow.engine.model.TaskModel.TaskType;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.entity.TaskActor;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
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
			FlowExecutor executor = FlowExecutorFactory.build(target);
			executor.execute(execution, target);
		}
	}

	private void createTask(Execution execution, TaskModel taskModel)
	{
		List<Task> tasks = createTask(taskModel, execution);
		execution.addTasks(tasks);
	}

	private void startSubProcess(Execution execution, SubProcessModel subProcessModel)
	{
		// 根据子流程模型名称获取子流程定义对象
		ProcessEngineConfiguration engineConfiguration = execution.getEngineConfiguration();
		ProcessDefinition processDefinition = engineConfiguration.getRepositoryService()
		        .getByVersion(subProcessModel.getProcessName(), subProcessModel.getVersion());

		Execution child = execution.createSubExecution(execution, processDefinition, subProcessModel.getName());
		ProcessInstance instance = startInstanceByExecution(child);

		Assert.notNull(instance, "子流程创建失败");

		execution.addTasks(engineConfiguration.getTaskService().getActiveTasks(instance.getId()));
	}

	public ProcessInstance startInstanceByExecution(Execution execution)
	{
		ProcessDefinition processDefinition = execution.getProcessDefinition();
		StartModel startModel = processDefinition.getModel().getStartModel();

		ProcessInstance instance = ProcessInstanceUtils.createProcessInstance(processDefinition, null, execution.getOperator(),
		        execution.getArgs(), execution.getParentInstance().getId(), execution.getParentNodeName(),
		        execution.getEngineConfiguration());

		Execution current = new Execution(execution.getEngineConfiguration(), processDefinition, instance, execution.getArgs());
		current.setOperator(execution.getOperator());

		FlowExecutor executor = FlowExecutorFactory.build(startModel);
		executor.execute(current, startModel);

		return current.getProcessInstance();
	}

	public List<Task> createTask(TaskModel taskModel, Execution execution)
	{
		List<Task> tasks = new ArrayList<Task>();

		Map<String, Object> args = execution.getArgs();
		if (args == null)
			args = new HashMap<String, Object>();
		LocalDate expireDate = DateHelper.processTime(args, taskModel.getExpireTime());
		LocalDate remindDate = DateHelper.processTime(args, taskModel.getReminderTime());
		String form = (String) args.get(taskModel.getForm());
		String actionUrl = StringUtils.isBlank(form) ? taskModel.getForm() : form;

		String[] actors = getTaskActors(taskModel, execution);
		args.put(Task.KEY_ACTOR, StringHelper.getStringByArray(actors));
		Task task = createTaskBase(taskModel, execution);
		task.setActionUrl(actionUrl);
		task.setExpireDate(expireDate);
		if (null != expireDate)
		{
			task.setExpireTime(expireDate.atStartOfDay());
		}
		task.setVariable(JsonHelper.toJson(args));

		if (taskModel.isPerformAny())
		{
			// 任务执行方式为参与者中任何一个执行即可驱动流程继续流转，该方法只产生一个task
			task = saveTask(execution, task, actors);
			task.setRemindDate(remindDate);
			tasks.add(task);
		}
		else if (taskModel.isPerformAll())
		{
			// 任务执行方式为参与者中每个都要执行完才可驱动流程继续流转，该方法根据参与者个数产生对应的task数量
			for (String actor : actors)
			{
				Task singleTask;
				try
				{
					singleTask = (Task) task.clone();
				}
				catch (CloneNotSupportedException e)
				{
					singleTask = task;
				}
				singleTask = saveTask(execution, singleTask, actor);
				singleTask.setRemindDate(remindDate);
				tasks.add(singleTask);
			}
		}
		return tasks;
	}

	/**
	 * 由DBAccess实现类持久化task对象
	 */
	private Task saveTask(Execution execution, Task task, String... actors)
	{
		TaskEntityService taskEntityService = execution.getEngineConfiguration().getTaskEntityService();

		task.setId(StringHelper.getPrimaryKey());
		task.setPerformType(PerformType.ANY.ordinal());
		taskEntityService.addEntity(task);
		assignTask(execution, task.getId(), actors);
		task.setActorIds(actors);
		return task;
	}

	/**
	 * 根据模型、执行对象、任务类型构建基本的task对象
	 * 
	 * @param model 模型
	 * @param execution 执行对象
	 * @return Task任务对象
	 */
	private Task createTaskBase(TaskModel model, Execution execution)
	{
		Task task = new Task();
		task.setInstanceId(execution.getProcessInstance().getId());
		task.setTaskName(model.getName());
		task.setDisplayName(model.getDisplayName());
		task.setCreateTime(LocalDateTime.now());
		if (model.isMajor())
		{
			task.setTaskType(TaskType.Major.ordinal());
		}
		else
		{
			task.setTaskType(TaskType.Aidant.ordinal());
		}
		task.setParentTaskId(execution.getTask() == null ? START : execution.getTask().getId());
		task.setModel(model);
		return task;
	}

	/**
	 * 根据Task模型的assignee、assignmentHandler属性以及运行时数据，确定参与者
	 * 
	 * @param model 模型
	 * @param execution 执行对象
	 * @return 参与者数组
	 */
	private String[] getTaskActors(TaskModel model, Execution execution)
	{
		Object assigneeObject = null;
		AssignmentHandler handler = model.getAssignmentHandlerObject();
		if (!StringUtils.isBlank(model.getAssignee()))
		{
			assigneeObject = execution.getArgs().get(model.getAssignee());
		}
		else if (handler != null)
		{
			if (handler instanceof Assignment)
			{
				assigneeObject = ((Assignment) handler).assign(model, execution);
			}
			else
			{
				assigneeObject = handler.assign(execution);
			}
		}
		return getTaskActors(assigneeObject == null ? model.getAssignee() : assigneeObject);
	}

	/**
	 * 根据taskmodel指定的assignee属性，从args中取值
	 * 将取到的值处理为String[]类型。
	 * 
	 * @param actors 参与者对象
	 * @return 参与者数组
	 */
	private String[] getTaskActors(Object actors)
	{
		if (actors == null)
			return null;
		String[] results;
		if (actors instanceof String)
		{
			// 如果值为字符串类型，则使用逗号,分隔
			return ((String) actors).split(",");
		}
		else if (actors instanceof List)
		{
			// jackson会把stirng[]转成arraylist，此处增加arraylist的逻辑判断,by 红豆冰沙2014.11.21
			List<?> list = (List) actors;
			results = new String[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				results[i] = (String) list.get(i);
			}
			return results;
		}
		else if (actors instanceof Long)
		{
			// 如果为Long类型，则返回1个元素的String[]
			results = new String[1];
			results[0] = String.valueOf((Long) actors);
			return results;
		}
		else if (actors instanceof Integer)
		{
			// 如果为Integer类型，则返回1个元素的String[]
			results = new String[1];
			results[0] = String.valueOf((Integer) actors);
			return results;
		}
		else if (actors instanceof String[])
		{
			// 如果为String[]类型，则直接返回
			return (String[]) actors;
		}
		else
		{
			// 其它类型，抛出不支持的类型异常
			throw new ProcessException("任务参与者对象[" + actors + "]类型不支持." + "合法参数示例:Long,Integer,new String[]{},'10000,20000',List<String>");
		}
	}

	/**
	 * 对指定的任务分配参与者。参与者可以为用户、部门、角色
	 * 
	 * @param taskId 任务id
	 * @param actorIds 参与者id集合
	 */
	private void assignTask(Execution execution, String taskId, String... actorIds)
	{
		if (actorIds == null || actorIds.length == 0)
			return;
		for (String actorId : actorIds)
		{
			// 修复当actorId为null的bug
			if (StringUtils.isBlank(actorId))
				continue;
			TaskActor taskActor = new TaskActor();
			taskActor.setTaskId(taskId);
			taskActor.setActorId(actorId);

			TaskActorEntityService taskActorEntityService = execution.getEngineConfiguration().getTaskActorEntityService();
			taskActorEntityService.addEntity(taskActor);
		}
	}
}
