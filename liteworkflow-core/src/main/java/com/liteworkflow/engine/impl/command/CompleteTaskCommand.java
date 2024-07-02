package com.liteworkflow.engine.impl.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Executor;
import com.liteworkflow.engine.impl.GeneralCompletion;
import com.liteworkflow.engine.impl.executor.ExecutorBuilder;
import com.liteworkflow.engine.impl.strategy.GeneralAccessStrategy;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.entity.TaskActor;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;

/**
 * TODO
 *
 * @version
 */
public class CompleteTaskCommand implements Command<Void>
{
	private static final Logger log = LoggerFactory.getLogger(CompleteTaskCommand.class);

	private String taskId;

	private String operator;

	private Map<String, Object> args;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param operator
	 * @param args
	 */
	public CompleteTaskCommand(String taskId, String operator, Map<String, Object> args)
	{
		super();
		this.taskId = taskId;
		this.operator = operator;
		this.args = args;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext commandContext)
	{
		// 完成任务，并且构造执行对象
		Execution execution = execute(taskId, operator, args, commandContext);
		if (execution == null)
			return null;

		ProcessModel model = execution.getProcessDefinition().getModel();
		if (model != null)
		{
			NodeModel nodeModel = model.getNodeModel(execution.getTask().getTaskName());
			// 将执行对象交给该任务对应的节点模型执行
			Executor executor = ExecutorBuilder.build(nodeModel);
			executor.execute(execution, nodeModel);
		}

		return null;
	}

	private Execution execute(String taskId, String operator, Map<String, Object> args, CommandContext commandContext)
	{
		if (args == null)
			args = new HashMap<String, Object>();
		Task task = complete(taskId, operator, args, commandContext);

		log.debug("任务[taskId=" + taskId + "]已完成");

		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		ProcessInstanceService processInstanceService = processEngineConfiguration.getProcessInstanceService();

		ProcessInstance instance = processInstanceService.getInstance(task.getInstanceId());
		Assert.notNull(instance, "指定的流程实例[id=" + task.getInstanceId() + "]已完成或不存在");
		processInstanceService.updateInstance(instance);
		// 协办任务完成不产生执行对象
		if (!task.isMajor())
		{
			return null;
		}
		Map<String, Object> instanceVarMaps = instance.getVariableMap();
		if (instanceVarMaps != null)
		{
			for (Map.Entry<String, Object> entry : instanceVarMaps.entrySet())
			{
				if (args.containsKey(entry.getKey()))
				{
					continue;
				}
				args.put(entry.getKey(), entry.getValue());
			}
		}

		RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

		ProcessDefinition process = repositoryService.getById(instance.getProcessDefinitionId());
		Execution execution = new Execution(processEngineConfiguration, process, instance, args);
		execution.setOperator(operator);
		execution.setTask(task);
		return execution;
	}

	public Task complete(String taskId, String operator, Map<String, Object> args, CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		TaskEntityService taskEntityService = processEngineConfiguration.getTaskEntityService();
		TaskActorEntityService taskActorEntityService = processEngineConfiguration.getTaskActorEntityService();
		HistoricTaskEntityService historicTaskEntityService = processEngineConfiguration.getHistoricTaskEntityService();

		Task task = taskEntityService.getTask(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		task.setVariable(JsonHelper.toJson(args));
		if (!isAllowed(task, operator, commandContext))
		{
			throw new ProcessException("当前参与者[" + operator + "]不允许执行任务[taskId=" + taskId + "]");
		}
		HistoricTask historicTask = new HistoricTask(task);
		historicTask.setFinishTime(LocalDateTime.now());
		historicTask.setTaskState(Constants.STATE_FINISH);
		historicTask.setOperator(operator);
		if (historicTask.getActorIds() == null)
		{
			List<TaskActor> actors = taskActorEntityService.queryByTaskId(task.getId());
			String[] actorIds = new String[actors.size()];
			for (int i = 0; i < actors.size(); i++)
			{
				actorIds[i] = actors.get(i).getActorId();
			}
			historicTask.setActorIds(actorIds);
		}

		historicTaskEntityService.addEntity(historicTask);

		taskActorEntityService.deleteByTaskId(task.getId());
		taskEntityService.deleteEntity(task);

		Completion completion = new GeneralCompletion();
		if (completion != null)
		{
			completion.complete(historicTask);
		}
		return task;
	}

	public boolean isAllowed(Task task, String operator, CommandContext commandContext)
	{
		if (!StringUtils.isBlank(operator))
		{
			if (ProcessEngine.ADMIN.equalsIgnoreCase(operator) || ProcessEngine.AUTO.equalsIgnoreCase(operator))
			{
				return true;
			}
			if (!StringUtils.isBlank(task.getOperator()))
			{
				return operator.equals(task.getOperator());
			}
		}

		TaskActorEntityService taskActorEntityService = commandContext.getProcessEngineConfiguration().getTaskActorEntityService();

		List<TaskActor> actors = taskActorEntityService.queryByTaskId(task.getId());
		if (actors == null || actors.isEmpty())
			return true;

		return !StringUtils.isBlank(operator) && new GeneralAccessStrategy().isAllowed(operator, actors);
	}
}
