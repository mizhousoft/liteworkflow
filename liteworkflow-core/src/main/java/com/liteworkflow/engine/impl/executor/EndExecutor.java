package com.liteworkflow.engine.impl.executor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.impl.Constants;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.util.HistoricTaskUtils;

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
		List<Task> tasks = engine.getTaskService().queryByInstanceId(instance.getId());
		if (!tasks.isEmpty())
		{
			throw new WorkFlowException("存在未完成的主办任务,请确认.");
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
	}

	public Task complete(String taskId, String operator, Map<String, Object> variableMap, Execution execution)
	{
		ProcessEngineConfigurationImpl engine = execution.getEngineConfiguration();
		TaskEntityService taskEntityService = engine.getTaskEntityService();
		HistoricTaskEntityService historicTaskEntityService = engine.getHistoricTaskEntityService();

		Task task = taskEntityService.getById(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		task.setVariable(JsonHelper.toJson(variableMap));

		HistoricTask historicTask = HistoricTaskUtils.createHistoricTask(task);
		historicTask.setEndTime(LocalDateTime.now());
		historicTask.setState(Constants.STATE_FINISH);
		historicTask.setOperator(operator);
		historicTaskEntityService.addEntity(historicTask);

		taskEntityService.deleteEntity(task);

		return task;
	}
}
