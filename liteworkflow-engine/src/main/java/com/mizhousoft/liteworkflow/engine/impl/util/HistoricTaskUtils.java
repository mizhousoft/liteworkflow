package com.mizhousoft.liteworkflow.engine.impl.util;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;

/**
 * 工具类
 *
 * @version
 */
public abstract class HistoricTaskUtils
{
	/**
	 * 创建历史任务
	 * 
	 * @param task
	 * @param engineConfiguration
	 * @return
	 */
	public static HistoricTaskEntity createHistoricTask(TaskEntity task, ProcessEngineConfigurationImpl engineConfiguration)
	{
		HistoricTaskEntity historicTask = new HistoricTaskEntity();
		historicTask.setId(task.getId());
		historicTask.setParentTaskId(task.getParentTaskId());
		historicTask.setProcessDefinitionId(task.getProcessDefinitionId());
		historicTask.setInstanceId(task.getInstanceId());
		historicTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
		historicTask.setName(task.getName());
		historicTask.setAssignee(task.getAssignee());
		historicTask.setStatus(task.getStatus());
		historicTask.setStartTime(task.getCreateTime());

		HistoricTaskEntityService historicTaskEntityService = engineConfiguration.getHistoricTaskEntityService();
		historicTaskEntityService.addEntity(historicTask);

		return historicTask;
	}

}
