package com.liteworkflow.engine.util;

import java.time.LocalDateTime;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.Task;

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
	 * @return
	 */
	public static HistoricTask createHistoricTask(Task task)
	{
		HistoricTask historicTask = new HistoricTask();
		historicTask.setId(task.getId());
		historicTask.setProcessDefinitionId(task.getProcessDefinitionId());
		historicTask.setInstanceId(task.getInstanceId());
		historicTask.setName(task.getName());
		historicTask.setTaskDefinitionId(task.getTaskDefinitionId());
		historicTask.setAssignee(task.getAssignee());
		historicTask.setTaskType(task.getTaskType());
		historicTask.setParentTaskId(task.getParentTaskId());
		historicTask.setVariable(task.getVariable());
		historicTask.setStartTime(task.getCreateTime());
		historicTask.setEndTime(LocalDateTime.now());

		return historicTask;
	}
}
