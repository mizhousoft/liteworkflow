package com.mizhousoft.liteworkflow.engine.impl.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.constant.TaskStatusEnum;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;

/**
 * 工具类
 *
 */
public abstract class TaskUtils
{
	/**
	 * 构建任务
	 * 
	 * @param userTask
	 * @param processInstance
	 * @return
	 */
	public static TaskEntity buildTask(UserTaskModel userTask, ProcessInstanceEntity processInstance)
	{
		return buildTask(userTask, processInstance.getProcessDefinitionId(), processInstance.getId());
	}

	/**
	 * 构建任务
	 * 
	 * @param userTask
	 * @param processDefinitionId
	 * @param instanceId
	 * @return
	 */
	public static TaskEntity buildTask(UserTaskModel userTask, int processDefinitionId, int instanceId)
	{
		TaskEntity task = new TaskEntity();
		task.setParentTaskId(0);
		task.setProcessDefinitionId(processDefinitionId);
		task.setInstanceId(instanceId);
		task.setTaskDefinitionKey(userTask.getId());
		task.setName(userTask.getName());
		task.setAssignee(null);
		task.setStatus(TaskStatusEnum.CREATED.getValue());
		task.setDueTime(null);
		task.setRevision(0);
		task.setCreateTime(LocalDateTime.now());

		return task;
	}

	/***
	 * 完成任务
	 * 
	 * @param task
	 * @param status
	 * @param variableMap
	 * @param engineConfiguration
	 * @return
	 */
	public static HistoricTaskEntity complete(TaskEntity task, TaskStatusEnum status, Map<String, Object> variableMap,
	        ProcessEngineConfigurationImpl engineConfiguration)
	{
		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();

		HistoricTaskEntity historicTask = historicTaskEntityService.loadById(task.getId());
		historicTask.setStatus(status.getValue());
		historicTask.setEndTime(LocalDateTime.now());

		long duration = ChronoUnit.SECONDS.between(historicTask.getStartTime(), historicTask.getEndTime());
		historicTask.setDuration(duration);

		historicTask.setRevision(historicTask.getRevision() + 1);
		historicTaskEntityService.modifyEntity(historicTask);

		HistoricVariableEntityService historicVariableEntityService = engineConfiguration.getHistoricVariableEntityService();
		historicVariableEntityService.updateVariables(task.getInstanceId(), task.getId(), variableMap);

		VariableEntityService variableEntityService = engineConfiguration.getVariableEntityService();
		variableEntityService.deleteByTaskId(task.getInstanceId(), task.getId());

		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		taskEntityService.deleteEntity(task);

		return historicTask;
	}
}
