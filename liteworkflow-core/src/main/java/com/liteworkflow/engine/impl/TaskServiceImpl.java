package com.liteworkflow.engine.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.command.CompleteTaskCommand;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.entity.TaskActor;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 任务执行业务类
 * 
 * @version
 */
public class TaskServiceImpl extends CommonServiceImpl implements TaskService
{
	private static final String START = "start";

	private TaskEntityService taskEntityService;

	private TaskActorEntityService taskActorEntityService;

	private HistoricTaskEntityService historicTaskEntityService;

	/**
	 * 流程定义业务类
	 */
	protected RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	protected ProcessInstanceService processInstanceService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public TaskServiceImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super(engineConfiguration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId)
	{
		return executeTask(taskId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator)
	{
		return executeTask(taskId, operator, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args)
	{
		commandExecutor.execute(new CompleteTaskCommand(taskId, operator, args));

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task getTask(String taskId)
	{
		return taskEntityService.getTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> getActiveTasks(String instanceId)
	{
		return taskEntityService.queryByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> getActiveTasks(TaskPageRequest request)
	{
		return taskEntityService.queryList(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Task> queryPageData(TaskPageRequest request)
	{
		return taskEntityService.queryPageData(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTaskActor(String taskId, String... actors)
	{
		addTaskActor(taskId, null, actors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTaskActor(String taskId, Integer performType, String... actors)
	{
		Task task = taskEntityService.getTask(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
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
				taskEntityService.modifyEntity(task);
				break;
			case 1:
				try
				{
					for (String actor : actors)
					{
						Task newTask = (Task) task.clone();
						newTask.setId(StringHelper.getPrimaryKey());
						newTask.setCreateTime(LocalDateTime.now());
						newTask.setOperator(actor);
						Map<String, Object> taskData = task.getVariableMap();
						taskData.put(Task.KEY_ACTOR, actor);
						task.setVariable(JsonHelper.toJson(taskData));
						taskEntityService.addEntity(newTask);
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
	 * {@inheritDoc}
	 */
	@Override
	public void removeTaskActor(String taskId, String... actors)
	{
		Task task = taskEntityService.getTask(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		if (actors == null || actors.length == 0)
			return;
		if (task.isMajor())
		{
			taskActorEntityService.removeTaskActor(task.getId(), actors);
			Map<String, Object> taskData = task.getVariableMap();
			String actorStr = (String) taskData.get(Task.KEY_ACTOR);
			if (!StringUtils.isBlank(actorStr))
			{
				String[] actorArray = actorStr.split(",");
				StringBuilder newActor = new StringBuilder(actorStr.length());
				boolean isMatch;
				for (String actor : actorArray)
				{
					isMatch = false;
					if (StringUtils.isBlank(actor))
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
				taskEntityService.modifyEntity(task);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task withdrawTask(String taskId, String operator)
	{
		HistoricTask hist = historicTaskEntityService.getByTaskId(taskId);
		Assert.notNull(hist, "指定的历史任务[id=" + taskId + "]不存在");
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
			taskEntityService.deleteEntity(task);
		}

		Task task = hist.undoTask();
		task.setId(StringHelper.getPrimaryKey());
		task.setCreateTime(LocalDateTime.now());
		taskEntityService.addEntity(task);
		assignTask(task.getId(), task.getOperator());
		return task;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Task rejectTask(ProcessModel model, Task currentTask)
	{
		String parentTaskId = currentTask.getParentTaskId();
		if (StringUtils.isBlank(parentTaskId) || parentTaskId.equals(START))
		{
			throw new ProcessException("上一步任务ID为空，无法驳回至上一步处理");
		}
		NodeModel current = model.getNodeModel(currentTask.getTaskName());
		HistoricTask historicTask = historicTaskEntityService.getByTaskId(parentTaskId);
		NodeModel parent = model.getNodeModel(historicTask.getTaskName());
		if (!NodeModel.canRejected(current, parent))
		{
			throw new ProcessException("无法驳回至上一步处理，请确认上一步骤并非fork、join、suprocess以及会签任务");
		}

		Task task = historicTask.undoTask();
		task.setId(StringHelper.getPrimaryKey());
		task.setCreateTime(LocalDateTime.now());
		task.setOperator(historicTask.getOperator());
		taskEntityService.addEntity(task);
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
			if (StringUtils.isBlank(actorId))
				continue;
			TaskActor taskActor = new TaskActor();
			taskActor.setTaskId(taskId);
			taskActor.setActorId(actorId);

			taskActorEntityService.addEntity(taskActor);
		}
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
	 * 设置historicTaskEntityService
	 * 
	 * @param historicTaskEntityService
	 */
	public void setHistoricTaskEntityService(HistoricTaskEntityService historicTaskEntityService)
	{
		this.historicTaskEntityService = historicTaskEntityService;
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
