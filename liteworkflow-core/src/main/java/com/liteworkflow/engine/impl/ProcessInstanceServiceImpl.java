package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程实例业务类
 * 
 * @author
 * @since 1.0
 */
public class ProcessInstanceServiceImpl implements ProcessInstanceService
{
	private ProcessInstanceEntityService processInstanceEntityService;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	private TaskEntityService taskEntityService;

	private HistoricTaskEntityService historicTaskEntityService;

	@Override
	public ProcessInstance getInstance(String instanceId)
	{
		return processInstanceEntityService.getById(instanceId);
	}

	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request)
	{
		return processInstanceEntityService.queryPageData(request);
	}

	/**
	 * 更新活动实例的last_updator、last_update_time、expire_time、revision、variable
	 */
	@Override
	public void updateInstance(ProcessInstance instance)
	{
		processInstanceEntityService.modifyEntity(instance);
	}

	/**
	 * 级联删除指定流程实例的所有数据：
	 * 1.wf_process_instance,wf_historic_process_instance
	 * 2.wf_task,wf_historic_task
	 * 3.wf_task_actor,wf_historic_task_actor
	 * 4.wf_cc_process_instance
	 * 
	 * @param id 实例id
	 */
	@Override
	public void cascadeRemove(String id)
	{
		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getByInstanceId(id);

		List<Task> activeTasks = taskEntityService.queryByInstanceId(id);
		List<HistoricTask> historicTasks = historicTaskEntityService.queryByInstanceId(id);
		for (Task task : activeTasks)
		{
			taskEntityService.deleteEntity(task);
		}
		for (HistoricTask historicTask : historicTasks)
		{
			historicTaskEntityService.deleteEntity(historicTask);
		}

		ProcessInstance instance = processInstanceEntityService.getById(id);
		historicProcessInstanceEntityService.deleteEntity(historicInstance);
		if (instance != null)
		{
			processInstanceEntityService.deleteEntity(instance);
		}
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
	 * 设置taskEntityService
	 * 
	 * @param taskEntityService
	 */
	public void setTaskEntityService(TaskEntityService taskEntityService)
	{
		this.taskEntityService = taskEntityService;
	}

	/**
	 * 设置historicProcessInstanceEntityService
	 * 
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
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
}
