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
 * @version
 */
public class ProcessInstanceServiceImpl implements ProcessInstanceService
{
	/**
	 * ProcessInstanceEntityService
	 */
	private ProcessInstanceEntityService processInstanceEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * TaskEntityService
	 */
	private TaskEntityService taskEntityService;

	/**
	 * HistoricTaskEntityService
	 */
	private HistoricTaskEntityService historicTaskEntityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance getInstance(String instanceId)
	{
		return processInstanceEntityService.getById(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request)
	{
		return processInstanceEntityService.queryPageData(request);
	}

	/**
	 * {@inheritDoc}
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
