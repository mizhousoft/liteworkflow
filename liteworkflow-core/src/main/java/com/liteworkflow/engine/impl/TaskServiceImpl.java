package com.liteworkflow.engine.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Assignment;
import com.liteworkflow.engine.AssignmentHandler;
import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.strategy.GeneralAccessStrategy;
import com.liteworkflow.engine.model.CustomModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TaskModel.PerformType;
import com.liteworkflow.engine.model.TaskModel.TaskType;
import com.liteworkflow.engine.persistence.entity.HistoryTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.entity.TaskActor;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.HistoryTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.model.TransitionModel;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务执行业务类
 * 
 * @author yuqs
 * @since 1.0
 */
public class TaskServiceImpl extends AccessService implements TaskService
{
	private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	private static final String START = "start";

	private ProcessEngineConfiguration engineConfiguration;

	private TaskEntityService taskEntityService;

	private TaskActorEntityService taskActorEntityService;

	private HistoryTaskEntityService historyTaskEntityService;

	private ProcessInstanceEntityService processInstanceEntityService;

	/**
	 * 流程定义业务类
	 */
	protected RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	protected ProcessInstanceService processInstanceService;

	// 访问策略接口
	private TaskAccessStrategy strategy = null;

	/**
	 * 根据任务主键ID执行任务
	 */
	@Override
	public List<Task> executeTask(String taskId)
	{
		return executeTask(taskId, null);
	}

	/**
	 * 根据任务主键ID，操作人ID执行任务
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator)
	{
		return executeTask(taskId, operator, null);
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args)
	{
		// 完成任务，并且构造执行对象
		Execution execution = execute(taskId, operator, args);
		if (execution == null)
			return Collections.emptyList();
		ProcessModel model = execution.getProcess().getModel();
		if (model != null)
		{
			NodeModel nodeModel = model.getNode(execution.getTask().getTaskName());
			// 将执行对象交给该任务对应的节点模型执行
			nodeModel.execute(execution);
		}
		return execution.getTasks();
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点
	 * 1、nodeName为null时，则驳回至上一步处理
	 * 2、nodeName不为null时，则任意跳转，即动态创建转移
	 */
	@Override
	public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName)
	{
		Execution execution = execute(taskId, operator, args);
		if (execution == null)
			return Collections.emptyList();
		ProcessModel model = execution.getProcess().getModel();
		AssertHelper.notNull(model, "当前任务未找到流程定义模型");
		if (StringHelper.isEmpty(nodeName))
		{
			Task newTask = rejectTask(model, execution.getTask());
			execution.addTask(newTask);
		}
		else
		{
			NodeModel nodeModel = model.getNode(nodeName);
			AssertHelper.notNull(nodeModel, "根据节点名称[" + nodeName + "]无法找到节点模型");
			// 动态创建转移对象，由转移对象执行execution实例
			TransitionModel tm = new TransitionModel();
			tm.setTarget(nodeModel);
			tm.setEnabled(true);
			tm.execute(execution);
		}

		return execution.getTasks();
	}

	/**
	 * 根据流程实例ID，操作人ID，参数列表按照节点模型model创建新的自由任务
	 */
	@Override
	public List<Task> createFreeTask(String instanceId, String operator, Map<String, Object> args, TaskModel model)
	{
		ProcessInstance order = processInstanceService.getOrder(instanceId);
		AssertHelper.notNull(order, "指定的流程实例[id=" + instanceId + "]已完成或不存在");
		order.setLastUpdator(operator);
		order.setLastUpdateTime(DateHelper.getTime());
		ProcessDefinition process = repositoryService.getProcessById(order.getProcessId());
		Execution execution = new Execution(engineConfiguration, process, order, args);
		execution.setOperator(operator);
		return createTask(model, execution);
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表完成任务，并且构造执行对象
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人
	 * @param args 参数列表
	 * @return Execution
	 */
	private Execution execute(String taskId, String operator, Map<String, Object> args)
	{
		if (args == null)
			args = new HashMap<String, Object>();
		Task task = complete(taskId, operator, args);

		log.debug("任务[taskId=" + taskId + "]已完成");

		ProcessInstance order = processInstanceService.getOrder(task.getInstanceId());
		AssertHelper.notNull(order, "指定的流程实例[id=" + task.getInstanceId() + "]已完成或不存在");
		order.setLastUpdator(operator);
		order.setLastUpdateTime(DateHelper.getTime());
		processInstanceService.updateOrder(order);
		// 协办任务完成不产生执行对象
		if (!task.isMajor())
		{
			return null;
		}
		Map<String, Object> orderMaps = order.getVariableMap();
		if (orderMaps != null)
		{
			for (Map.Entry<String, Object> entry : orderMaps.entrySet())
			{
				if (args.containsKey(entry.getKey()))
				{
					continue;
				}
				args.put(entry.getKey(), entry.getValue());
			}
		}
		ProcessDefinition process = repositoryService.getProcessById(order.getProcessId());
		Execution execution = new Execution(engineConfiguration, process, order, args);
		execution.setOperator(operator);
		execution.setTask(task);
		return execution;
	}

	@Override
	public Task getTask(String taskId)
	{
		return taskEntityService.getTask(taskId);
	}

	@Override
	public String[] getTaskActorsByTaskId(String taskId)
	{
		List<TaskActor> actors = taskActorEntityService.getTaskActorsByTaskId(taskId);
		if (actors == null || actors.isEmpty())
			return null;
		String[] actorIds = new String[actors.size()];
		for (int i = 0; i < actors.size(); i++)
		{
			TaskActor ta = actors.get(i);
			actorIds[i] = ta.getActorId();
		}
		return actorIds;
	}

	@Override
	public List<Task> getActiveTasks(String instanceId)
	{
		return taskEntityService.queryByInstanceId(instanceId);
	}

	@Override
	public List<Task> getActiveTasks(TaskPageRequest request)
	{
		return taskEntityService.queryList(request);
	}

	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		return taskEntityService.queryPageData(request);
	}

	/**
	 * 完成指定任务
	 */
	@Override
	public Task complete(String taskId)
	{
		return complete(taskId, null, null);
	}

	/**
	 * 完成指定任务
	 */
	@Override
	public Task complete(String taskId, String operator)
	{
		return complete(taskId, operator, null);
	}

	/**
	 * 完成指定任务
	 * 该方法仅仅结束活动任务，并不能驱动流程继续执行
	 * 
	 * @see ProcessEngineImpl#executeTask(String, String, java.util.Map)
	 */
	@Override
	public Task complete(String taskId, String operator, Map<String, Object> args)
	{
		Task task = taskEntityService.getTask(taskId);
		AssertHelper.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		task.setVariable(JsonHelper.toJson(args));
		if (!isAllowed(task, operator))
		{
			throw new ProcessException("当前参与者[" + operator + "]不允许执行任务[taskId=" + taskId + "]");
		}
		HistoryTask history = new HistoryTask(task);
		history.setFinishTime(DateHelper.getTime());
		history.setTaskState(Constants.STATE_FINISH);
		history.setOperator(operator);
		if (history.getActorIds() == null)
		{
			List<TaskActor> actors = taskActorEntityService.getTaskActorsByTaskId(task.getId());
			String[] actorIds = new String[actors.size()];
			for (int i = 0; i < actors.size(); i++)
			{
				actorIds[i] = actors.get(i).getActorId();
			}
			history.setActorIds(actorIds);
		}

		historyTaskEntityService.saveHistory(history);

		taskActorEntityService.deleteByTaskId(task.getId());
		taskEntityService.delete(task);

		Completion completion = getCompletion();
		if (completion != null)
		{
			completion.complete(history);
		}
		return task;
	}

	/**
	 * 任务历史记录方法
	 * 
	 * @param execution 执行对象
	 * @param model 自定义节点模型
	 * @return 历史任务对象
	 */
	@Override
	public HistoryTask history(Execution execution, CustomModel model)
	{
		HistoryTask historyTask = new HistoryTask();
		historyTask.setId(StringHelper.getPrimaryKey());
		historyTask.setInstanceId(execution.getInstance().getId());
		String currentTime = DateHelper.getTime();
		historyTask.setCreateTime(currentTime);
		historyTask.setFinishTime(currentTime);
		historyTask.setDisplayName(model.getDisplayName());
		historyTask.setTaskName(model.getName());
		historyTask.setTaskState(Constants.STATE_FINISH);
		historyTask.setTaskType(TaskType.Record.ordinal());
		historyTask.setParentTaskId(execution.getTask() == null ? START : execution.getTask().getId());
		historyTask.setVariable(JsonHelper.toJson(execution.getArgs()));

		historyTaskEntityService.saveHistory(historyTask);

		return historyTask;
	}

	/**
	 * 提取指定任务，设置完成时间及操作人，状态不改变
	 */
	@Override
	public Task take(String taskId, String operator)
	{
		Task task = taskEntityService.getTask(taskId);
		AssertHelper.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		if (!isAllowed(task, operator))
		{
			throw new ProcessException("当前参与者[" + operator + "]不允许提取任务[taskId=" + taskId + "]");
		}
		task.setOperator(operator);
		task.setFinishTime(DateHelper.getTime());

		taskEntityService.update(task);

		return task;
	}

	/**
	 * 唤醒指定的历史任务
	 */
	@Override
	public Task resume(String taskId, String operator)
	{
		HistoryTask histTask = historyTaskEntityService.getHistTask(taskId);
		AssertHelper.notNull(histTask, "指定的历史任务[id=" + taskId + "]不存在");
		boolean isAllowed = true;
		if (StringHelper.isNotEmpty(histTask.getOperator()))
		{
			isAllowed = histTask.getOperator().equals(operator);
		}
		if (isAllowed)
		{
			Task task = histTask.undoTask();
			task.setId(StringHelper.getPrimaryKey());
			task.setCreateTime(DateHelper.getTime());

			taskEntityService.save(task);

			assignTask(task.getId(), task.getOperator());
			return task;
		}
		else
		{
			throw new ProcessException("当前参与者[" + operator + "]不允许唤醒历史任务[taskId=" + taskId + "]");
		}
	}

	/**
	 * 向指定任务添加参与者
	 */
	@Override
	public void addTaskActor(String taskId, String... actors)
	{
		addTaskActor(taskId, null, actors);
	}

	/**
	 * 向指定任务添加参与者
	 * 该方法根据performType类型判断是否需要创建新的活动任务
	 */
	@Override
	public void addTaskActor(String taskId, Integer performType, String... actors)
	{
		Task task = taskEntityService.getTask(taskId);
		AssertHelper.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		if (!task.isMajor())
			return;
		if (performType == null)
			performType = task.getPerformType();
		if (performType == null)
			performType = 0;
		switch (performType)
		{
			case 0:
				assignTask(task.getId(), actors);
				Map<String, Object> data = task.getVariableMap();
				String oldActor = (String) data.get(Task.KEY_ACTOR);
				data.put(Task.KEY_ACTOR, oldActor + "," + StringHelper.getStringByArray(actors));
				task.setVariable(JsonHelper.toJson(data));
				taskEntityService.update(task);
				break;
			case 1:
				try
				{
					for (String actor : actors)
					{
						Task newTask = (Task) task.clone();
						newTask.setId(StringHelper.getPrimaryKey());
						newTask.setCreateTime(DateHelper.getTime());
						newTask.setOperator(actor);
						Map<String, Object> taskData = task.getVariableMap();
						taskData.put(Task.KEY_ACTOR, actor);
						task.setVariable(JsonHelper.toJson(taskData));
						taskEntityService.save(newTask);
						assignTask(newTask.getId(), actor);
					}
				}
				catch (CloneNotSupportedException ex)
				{
					throw new ProcessException("任务对象不支持复制", ex.getCause());
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 向指定任务移除参与者
	 */
	@Override
	public void removeTaskActor(String taskId, String... actors)
	{
		Task task = taskEntityService.getTask(taskId);
		AssertHelper.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		if (actors == null || actors.length == 0)
			return;
		if (task.isMajor())
		{
			taskActorEntityService.removeTaskActor(task.getId(), actors);
			Map<String, Object> taskData = task.getVariableMap();
			String actorStr = (String) taskData.get(Task.KEY_ACTOR);
			if (StringHelper.isNotEmpty(actorStr))
			{
				String[] actorArray = actorStr.split(",");
				StringBuilder newActor = new StringBuilder(actorStr.length());
				boolean isMatch;
				for (String actor : actorArray)
				{
					isMatch = false;
					if (StringHelper.isEmpty(actor))
						continue;
					for (String removeActor : actors)
					{
						if (actor.equals(removeActor))
						{
							isMatch = true;
							break;
						}
					}
					if (isMatch)
						continue;
					newActor.append(actor).append(",");
				}
				newActor.deleteCharAt(newActor.length() - 1);
				taskData.put(Task.KEY_ACTOR, newActor.toString());
				task.setVariable(JsonHelper.toJson(taskData));
				taskEntityService.update(task);
			}
		}
	}

	/**
	 * 撤回指定的任务
	 */
	@Override
	public Task withdrawTask(String taskId, String operator)
	{
		HistoryTask hist = historyTaskEntityService.getHistTask(taskId);
		AssertHelper.notNull(hist, "指定的历史任务[id=" + taskId + "]不存在");
		List<Task> tasks;
		if (hist.isPerformAny())
		{
			tasks = taskEntityService.getNextActiveTasks(hist.getId());
		}
		else
		{
			tasks = taskEntityService.getNextActiveTaskList(hist.getInstanceId(), hist.getTaskName(), hist.getParentTaskId());
		}
		if (tasks == null || tasks.isEmpty())
		{
			throw new ProcessException("后续活动任务已完成或不存在，无法撤回.");
		}
		for (Task task : tasks)
		{
			taskEntityService.delete(task);
		}

		Task task = hist.undoTask();
		task.setId(StringHelper.getPrimaryKey());
		task.setCreateTime(DateHelper.getTime());
		taskEntityService.save(task);
		assignTask(task.getId(), task.getOperator());
		return task;
	}

	/**
	 * 驳回任务
	 */
	@Override
	public Task rejectTask(ProcessModel model, Task currentTask)
	{
		String parentTaskId = currentTask.getParentTaskId();
		if (StringHelper.isEmpty(parentTaskId) || parentTaskId.equals(START))
		{
			throw new ProcessException("上一步任务ID为空，无法驳回至上一步处理");
		}
		NodeModel current = model.getNode(currentTask.getTaskName());
		HistoryTask history = historyTaskEntityService.getHistTask(parentTaskId);
		NodeModel parent = model.getNode(history.getTaskName());
		if (!NodeModel.canRejected(current, parent))
		{
			throw new ProcessException("无法驳回至上一步处理，请确认上一步骤并非fork、join、suprocess以及会签任务");
		}

		Task task = history.undoTask();
		task.setId(StringHelper.getPrimaryKey());
		task.setCreateTime(DateHelper.getTime());
		task.setOperator(history.getOperator());
		taskEntityService.save(task);
		assignTask(task.getId(), task.getOperator());
		return task;
	}

	/**
	 * 对指定的任务分配参与者。参与者可以为用户、部门、角色
	 * 
	 * @param taskId 任务id
	 * @param actorIds 参与者id集合
	 */
	private void assignTask(String taskId, String... actorIds)
	{
		if (actorIds == null || actorIds.length == 0)
			return;
		for (String actorId : actorIds)
		{
			// 修复当actorId为null的bug
			if (StringHelper.isEmpty(actorId))
				continue;
			TaskActor taskActor = new TaskActor();
			taskActor.setTaskId(taskId);
			taskActor.setActorId(actorId);

			taskActorEntityService.save(taskActor);
		}
	}

	/**
	 * 根据已有任务、任务类型、参与者创建新的任务
	 * 适用于转派，动态协办处理
	 */
	@Override
	public List<Task> createNewTask(String taskId, int taskType, String... actors)
	{
		Task task = taskEntityService.getTask(taskId);
		AssertHelper.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		List<Task> tasks = new ArrayList<Task>();
		try
		{
			Task newTask = (Task) task.clone();
			newTask.setTaskType(taskType);
			newTask.setCreateTime(DateHelper.getTime());
			newTask.setParentTaskId(taskId);
			tasks.add(saveTask(newTask, actors));
		}
		catch (CloneNotSupportedException e)
		{
			throw new ProcessException("任务对象不支持复制", e.getCause());
		}
		return tasks;
	}

	/**
	 * 获取任务模型
	 * 
	 * @param taskId 任务id
	 * @return TaskModel
	 */
	@Override
	public TaskModel getTaskModel(String taskId)
	{
		Task task = taskEntityService.getTask(taskId);

		ProcessInstance order = processInstanceEntityService.getOrder(task.getInstanceId());

		ProcessDefinition process = engineConfiguration.getRepositoryService().getProcessById(order.getProcessId());
		ProcessModel model = process.getModel();
		NodeModel nodeModel = model.getNode(task.getTaskName());
		AssertHelper.notNull(nodeModel, "任务id无法找到节点模型.");
		if (nodeModel instanceof TaskModel)
		{
			return (TaskModel) nodeModel;
		}
		else
		{
			throw new IllegalArgumentException("任务id找到的节点模型不匹配");
		}
	}

	/**
	 * 由DBAccess实现类创建task，并根据model类型决定是否分配参与者
	 * 
	 * @param taskModel 模型
	 * @param execution 执行对象
	 * @return List<Task> 任务列表
	 */
	@Override
	public List<Task> createTask(TaskModel taskModel, Execution execution)
	{
		List<Task> tasks = new ArrayList<Task>();

		Map<String, Object> args = execution.getArgs();
		if (args == null)
			args = new HashMap<String, Object>();
		Date expireDate = DateHelper.processTime(args, taskModel.getExpireTime());
		Date remindDate = DateHelper.processTime(args, taskModel.getReminderTime());
		String form = (String) args.get(taskModel.getForm());
		String actionUrl = StringHelper.isEmpty(form) ? taskModel.getForm() : form;

		String[] actors = getTaskActors(taskModel, execution);
		args.put(Task.KEY_ACTOR, StringHelper.getStringByArray(actors));
		Task task = createTaskBase(taskModel, execution);
		task.setActionUrl(actionUrl);
		task.setExpireDate(expireDate);
		task.setExpireTime(DateHelper.parseTime(expireDate));
		task.setVariable(JsonHelper.toJson(args));

		if (taskModel.isPerformAny())
		{
			// 任务执行方式为参与者中任何一个执行即可驱动流程继续流转，该方法只产生一个task
			task = saveTask(task, actors);
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
				singleTask = saveTask(singleTask, actor);
				singleTask.setRemindDate(remindDate);
				tasks.add(singleTask);
			}
		}
		return tasks;
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
		task.setInstanceId(execution.getInstance().getId());
		task.setTaskName(model.getName());
		task.setDisplayName(model.getDisplayName());
		task.setCreateTime(DateHelper.getTime());
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
	 * 由DBAccess实现类持久化task对象
	 */
	private Task saveTask(Task task, String... actors)
	{
		task.setId(StringHelper.getPrimaryKey());
		task.setPerformType(PerformType.ANY.ordinal());
		taskEntityService.save(task);
		assignTask(task.getId(), actors);
		task.setActorIds(actors);
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
		if (StringHelper.isNotEmpty(model.getAssignee()))
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
	 * 判断当前操作人operator是否允许执行taskId指定的任务
	 */
	@Override
	public boolean isAllowed(Task task, String operator)
	{
		if (StringHelper.isNotEmpty(operator))
		{
			if (ProcessEngine.ADMIN.equalsIgnoreCase(operator) || ProcessEngine.AUTO.equalsIgnoreCase(operator))
			{
				return true;
			}
			if (StringHelper.isNotEmpty(task.getOperator()))
			{
				return operator.equals(task.getOperator());
			}
		}
		List<TaskActor> actors = taskActorEntityService.getTaskActorsByTaskId(task.getId());
		if (actors == null || actors.isEmpty())
			return true;
		return !StringHelper.isEmpty(operator) && getStrategy().isAllowed(operator, actors);
	}

	public void setStrategy(TaskAccessStrategy strategy)
	{
		this.strategy = strategy;
	}

	public TaskAccessStrategy getStrategy()
	{
		if (strategy != null)
		{
			return strategy;
		}
		strategy = ServiceContext.find(TaskAccessStrategy.class);
		if (strategy == null)
		{
			ServiceContext.put(TaskAccessStrategy.class.getName(), GeneralAccessStrategy.class);
			strategy = ServiceContext.find(TaskAccessStrategy.class);
		}
		return strategy;
	}

	/**
	 * 设置taskEntityService
	 * 
	 * @param taskEntityService
	 */
	public void setTaskEntityService(TaskEntityService taskEntityService)
	{
		this.taskEntityService = taskEntityService;
	}

	/**
	 * 设置taskActorEntityService
	 * 
	 * @param taskActorEntityService
	 */
	public void setTaskActorEntityService(TaskActorEntityService taskActorEntityService)
	{
		this.taskActorEntityService = taskActorEntityService;
	}

	/**
	 * 设置historyTaskEntityService
	 * 
	 * @param historyTaskEntityService
	 */
	public void setHistoryTaskEntityService(HistoryTaskEntityService historyTaskEntityService)
	{
		this.historyTaskEntityService = historyTaskEntityService;
	}

	/**
	 * 设置processInstanceEntityService
	 * 
	 * @param processInstanceEntityService
	 */
	public void setProcessInstanceEntityService(ProcessInstanceEntityService processInstanceEntityService)
	{
		this.processInstanceEntityService = processInstanceEntityService;
	}

	/**
	 * 设置engineConfiguration
	 * 
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}

	/**
	 * 设置repositoryService
	 * 
	 * @param repositoryService
	 */
	public void setRepositoryService(RepositoryService repositoryService)
	{
		this.repositoryService = repositoryService;
	}

	/**
	 * 设置processInstanceService
	 * 
	 * @param processInstanceService
	 */
	public void setProcessInstanceService(ProcessInstanceService processInstanceService)
	{
		this.processInstanceService = processInstanceService;
	}
}
