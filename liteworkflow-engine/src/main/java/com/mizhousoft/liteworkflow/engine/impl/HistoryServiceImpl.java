package com.mizhousoft.liteworkflow.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.liteworkflow.engine.HistoryService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.HistoricProcessInstance;
import com.mizhousoft.liteworkflow.engine.domain.HistoricTask;
import com.mizhousoft.liteworkflow.engine.impl.command.DeleteHistoricInstanceCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.DeleteHistoricTaskCommand;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.request.HistoricInstancePageRequest;
import com.mizhousoft.liteworkflow.engine.request.HistoricTaskPageRequest;

/**
 * 历史数据服务
 *
 * @version
 */
public class HistoryServiceImpl extends CommonServiceImpl implements HistoryService
{
	/**
	 * HistoricTaskEntityService
	 */
	private HistoricTaskEntityService historicTaskEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * HistoricVariableEntityService
	 */
	private HistoricVariableEntityService historicVariableEntityService;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param historicTaskEntityService
	 * @param historicProcessInstanceEntityService
	 * @param historicVariableEntityService
	 */
	public HistoryServiceImpl(ProcessEngineConfigurationImpl engineConfiguration, HistoricTaskEntityService historicTaskEntityService,
	        HistoricProcessInstanceEntityService historicProcessInstanceEntityService,
	        HistoricVariableEntityService historicVariableEntityService)
	{
		super(engineConfiguration);

		this.historicTaskEntityService = historicTaskEntityService;
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
		this.historicVariableEntityService = historicVariableEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteHistoricInstance(int instanceId)
	{
		commandExecutor.execute(new DeleteHistoricInstanceCommand(instanceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricProcessInstance getHistoricInstance(int instanceId)
	{
		return historicProcessInstanceEntityService.getById(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricProcessInstance> queryHistoricInstances(Set<Integer> instanceIds)
	{
		List<HistoricInstanceEntity> list = historicProcessInstanceEntityService.queryByIds(instanceIds);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request)
	{
		Page<HistoricInstanceEntity> page = historicProcessInstanceEntityService.queryPageData(request);

		return PageBuilder.build(new ArrayList<>(page.getContent()), request, page.getTotalNumber());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricTaskPageRequest request)
	{
		Page<HistoricTaskEntity> page = historicTaskEntityService.queryPageData(request);

		Set<Integer> instanceIds = page.getContent().stream().map(o -> o.getInstanceId()).collect(Collectors.toSet());
		List<HistoricInstanceEntity> list = historicProcessInstanceEntityService.queryByIds(instanceIds);
		Map<Integer, HistoricInstanceEntity> instanceMap = list.stream().collect(Collectors.toMap(HistoricInstanceEntity::getId, o -> o));

		List<HistoricProcessInstance> instances = new ArrayList<>(list.size());
		for (HistoricTaskEntity historicTask : page.getContent())
		{
			HistoricInstanceEntity instance = instanceMap.get(historicTask.getInstanceId());
			if (null != instance)
			{
				instances.add(instance);
			}
		}

		return PageBuilder.build(instances, request, page.getTotalNumber());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteHistoricTask(int taskId)
	{
		commandExecutor.execute(new DeleteHistoricTaskCommand(taskId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricTask getHistoricTask(int taskId)
	{
		return historicTaskEntityService.getById(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricTask> queryHistoricTasks(int instanceId)
	{
		List<HistoricTaskEntity> list = historicTaskEntityService.queryByInstanceId(instanceId);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getHistoricInstanceVariableMap(int instanceId)
	{
		return historicVariableEntityService.queryMapByTaskId(instanceId, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> getHistoricTaskVariableMap(int instanceId, Set<Integer> taskIds)
	{
		return historicVariableEntityService.queryMapByTaskIds(instanceId, taskIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> getHistoricInstanceVariableMap(Set<Integer> instanceIds)
	{
		return historicVariableEntityService.queryMapByInstanceIds(instanceIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> getHistoricTaskVariableMap(Set<Integer> instanceIds, Set<Integer> taskIds)
	{
		return historicVariableEntityService.queryMapByTaskIds(instanceIds, taskIds);
	}
}
