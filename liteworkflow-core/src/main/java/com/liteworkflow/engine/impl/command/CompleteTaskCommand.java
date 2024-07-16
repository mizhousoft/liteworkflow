package com.liteworkflow.engine.impl.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.Constants;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.FlowExecutor;
import com.liteworkflow.engine.impl.executor.FlowExecutorFactory;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.util.HistoricTaskUtils;
import com.mizhousoft.commons.json.JSONUtils;

/**
 * 完成任务指令
 *
 * @version
 */
public class CompleteTaskCommand implements Command<Void>
{
	private static final Logger LOG = LoggerFactory.getLogger(CompleteTaskCommand.class);

	private int taskId;

	private Map<String, Object> variableMap;

	/**
	 * 构造函数
	 *
	 * @param taskId
	 * @param variableMap
	 */
	public CompleteTaskCommand(int taskId, Map<String, Object> variableMap)
	{
		super();
		this.taskId = taskId;
		this.variableMap = variableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Void execute(CommandContext commandContext)
	{
		// 完成任务，并且构造执行对象
		Execution execution = execute(taskId, variableMap, commandContext);
		if (execution == null)
			return null;

		BpmnModel bpmnModel = execution.getProcessDefinition().getBpmnModel();
		if (bpmnModel != null)
		{
			FlowNode nodeModel = bpmnModel.getFlowNodeModel(execution.getTask().getTaskDefinitionId());

			List<SequenceFlowModel> outgoingFlows = nodeModel.getOutgoingFlows();
			for (SequenceFlowModel outgoingFlow : outgoingFlows)
			{
				// 将执行对象交给该任务对应的节点模型执行
				FlowExecutor executor = FlowExecutorFactory.build(outgoingFlow);
				executor.execute(execution, outgoingFlow);
			}
		}

		return null;
	}

	private Execution execute(int taskId, Map<String, Object> variableMap, CommandContext commandContext)
	{
		if (variableMap == null)
			variableMap = new HashMap<String, Object>();
		Task task = complete(taskId, variableMap, commandContext);

		LOG.debug("任务[taskId=" + taskId + "]已完成");

		ProcessEngineConfigurationImpl engineConfiguration = commandContext.getEngineConfiguration();
		ProcessInstanceService processInstanceService = engineConfiguration.getProcessInstanceService();
		ProcessInstanceEntityService processInstanceEntityService = engineConfiguration.getProcessInstanceEntityService();

		ProcessInstance instance = processInstanceService.getInstance(task.getInstanceId());
		Assert.notNull(instance, "指定的流程实例[id=" + task.getInstanceId() + "]已完成或不存在");
		processInstanceEntityService.modifyEntity(instance);

		Map<String, Object> instanceVarMaps = instance.getVariableMap();
		if (instanceVarMaps != null)
		{
			for (Map.Entry<String, Object> entry : instanceVarMaps.entrySet())
			{
				if (variableMap.containsKey(entry.getKey()))
				{
					continue;
				}

				variableMap.put(entry.getKey(), entry.getValue());
			}
		}

		RepositoryService repositoryService = engineConfiguration.getRepositoryService();

		ProcessDefinition process = repositoryService.getProcessDefinition(instance.getProcessDefinitionId());
		Execution execution = new Execution(engineConfiguration, process, instance, variableMap);
		execution.setTask(task);

		return execution;
	}

	public Task complete(int taskId, Map<String, Object> variableMap, CommandContext commandContext)
	{
		ProcessEngineConfigurationImpl engineConfiguration = commandContext.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();

		Task task = taskEntityService.getById(taskId);
		Assert.notNull(task, "指定的任务[id=" + taskId + "]不存在");
		task.setVariable(JSONUtils.toJSONStringQuietly(variableMap));

		HistoricTask historicTask = HistoricTaskUtils.createHistoricTask(task);
		historicTask.setEndTime(LocalDateTime.now());
		historicTask.setState(Constants.STATE_FINISH);
		historicTaskEntityService.addEntity(historicTask);

		taskEntityService.deleteEntity(task);

		return task;
	}
}
