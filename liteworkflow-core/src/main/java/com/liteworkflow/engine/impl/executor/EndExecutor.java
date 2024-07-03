package com.liteworkflow.engine.impl.executor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.GeneralCompletion;
import com.liteworkflow.engine.impl.strategy.GeneralAccessStrategy;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.entity.TaskActor;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;

/**
 * TODO
 *
 * @version
 */
public class EndExecutor extends NodeFlowExecutor
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute(Execution execution, NodeModel nodeModel)
	{
		ProcessEngineConfigurationImpl engine = execution.getEngineConfiguration();
		ProcessInstance instance = execution.getProcessInstance();
		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			if (task.isMajor())
			{
				throw new ProcessException("存在未完成的主办任务,请确认.");
			}

			complete(task.getId(), ProcessEngine.AUTO, null, execution);
		}

		/**
		 * 结束当前流程实例
		 */
		complete(instance.getId(), execution);

		/**
		 * 如果存在父流程，则重新构造Execution执行对象，交给父流程的SubProcessModel模型execute
		 */
		if (!StringUtils.isBlank(instance.getParentId()))
		{
			ProcessInstance parentInstance = engine.getProcessInstanceService().getInstance(instance.getParentId());
			if (parentInstance == null)
			{
				return;
			}

			ProcessDefinition processDefinition = engine.getRepositoryService().getById(parentInstance.getProcessDefinitionId());
			ProcessModel processModel = processDefinition.getModel();
			if (processModel == null)
			{
				return;
			}

			SubProcessModel subProcessModel = (SubProcessModel) processModel.getNodeModel(instance.getParentNodeName());
			Execution newExecution = new Execution(engine, processDefinition, parentInstance, execution.getArgs());
			newExecution.setChildInstanceId(instance.getId());
			newExecution.setTask(execution.getTask());

			FlowExecutor executor = FlowExecutorFactory.build(subProcessModel);
			executor.execute(newExecution, subProcessModel);

			/**
			 * SubProcessModel执行结果的tasks合并到当前执行对象execution的tasks列表中
			 */
			execution.addTasks(newExecution.getTasks());
		}
	}

	public void complete(String instanceId, Execution execution)
	{
		ProcessEngineConfigurationImpl engine = execution.getEngineConfiguration();
		ProcessInstanceEntityService processInstanceEntityService = engine.getProcessInstanceEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engine.getHistoricProcessInstanceEntityService();

		ProcessInstance instance = processInstanceEntityService.getById(instanceId);
		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getByInstanceId(instanceId);
		historicInstance.setState(Constants.STATE_FINISH);
		historicInstance.setEndTime(LocalDateTime.now());

		historicProcessInstanceEntityService.modifyEntity(historicInstance);
		processInstanceEntityService.deleteEntity(instance);
		Completion completion = new GeneralCompletion();
		if (completion != null)
		{
			completion.complete(historicInstance);
		}
	}

	public Task complete(String taskId, String operator, Map<String, Object> args, Execution execution)
	{
		ProcessEngineConfigurationImpl engine = execution.getEngineConfiguration();
		TaskEntityService taskEntityService = engine.getTaskEntityService();
		TaskActorEntityService taskActorEntityService = engine.getTaskActorEntityService();
		HistoricTaskEntityService historicTaskEntityService = engine.getHistoricTaskEntityService();

		Task task = taskEntityService.getTask(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		task.setVariable(JsonHelper.toJson(args));
		if (!isAllowed(task, operator, engine))
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

		return task;
	}

	public boolean isAllowed(Task task, String operator, ProcessEngineConfigurationImpl processEngineConfiguration)
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

		TaskActorEntityService taskActorEntityService = processEngineConfiguration.getTaskActorEntityService();

		List<TaskActor> actors = taskActorEntityService.queryByTaskId(task.getId());
		if (actors == null || actors.isEmpty())
			return true;

		return !StringUtils.isBlank(operator) && new GeneralAccessStrategy().isAllowed(operator, actors);
	}
}
