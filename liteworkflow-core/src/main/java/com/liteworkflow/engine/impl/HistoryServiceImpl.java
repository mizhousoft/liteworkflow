package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.HistoricTaskActor;
import com.liteworkflow.engine.persistence.request.CCInstancePageRequest;
import com.liteworkflow.engine.persistence.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.service.CCProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskActorEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TODO
 *
 * @version
 */
public class HistoryServiceImpl implements HistoryService
{
	private HistoricTaskEntityService historicTaskEntityService;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	private CCProcessInstanceEntityService ccProcessInstanceEntityService;

	private HistoricTaskActorEntityService historicTaskActorEntityService;

	@Override
	public HistoricProcessInstance getHistoricInstance(String instanceId)
	{
		return historicProcessInstanceEntityService.getHistoricInstance(instanceId);
	}

	@Override
	public List<HistoricProcessInstance> getHistoricInstances(HistoricProcessInstPageRequest request)
	{
		return historicProcessInstanceEntityService.queryList(request);
	}

	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request)
	{
		return historicProcessInstanceEntityService.queryPageData(request);
	}

	@Override
	public HistoricTask getHistTask(String taskId)
	{
		return historicTaskEntityService.getHistTask(taskId);
	}

	@Override
	public String[] getHistoricTaskActorsByTaskId(String taskId)
	{
		List<HistoricTaskActor> actors = historicTaskActorEntityService.getHistTaskActorsByTaskId(taskId);
		if (actors == null || actors.isEmpty())
			return null;
		String[] actorIds = new String[actors.size()];
		for (int i = 0; i < actors.size(); i++)
		{
			HistoricTaskActor ta = actors.get(i);
			actorIds[i] = ta.getActorId();
		}
		return actorIds;
	}

	@Override
	public List<HistoricTask> getHistoricTasks(String instanceId)
	{
		return historicTaskEntityService.queryByInstanceId(instanceId);
	}

	@Override
	public List<HistoricTask> getHistoricTasks(TaskPageRequest request)
	{
		return historicTaskEntityService.queryList(request);
	}

	@Override
	public Page<HistoricProcessInstance> getCCWorks(CCInstancePageRequest request)
	{
		return ccProcessInstanceEntityService.queryPageData(request);
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
	 * 设置historicProcessInstanceEntityService
	 * 
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * 设置ccProcessInstanceEntityService
	 * 
	 * @param ccProcessInstanceEntityService
	 */
	public void setCcProcessInstanceEntityService(CCProcessInstanceEntityService ccProcessInstanceEntityService)
	{
		this.ccProcessInstanceEntityService = ccProcessInstanceEntityService;
	}

	/**
	 * 设置historicTaskActorEntityService
	 * 
	 * @param historicTaskActorEntityService
	 */
	public void setHistoricTaskActorEntityService(HistoricTaskActorEntityService historicTaskActorEntityService)
	{
		this.historicTaskActorEntityService = historicTaskActorEntityService;
	}
}
