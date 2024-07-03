package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;
import java.util.Set;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.mapper.HistoricProcessInstanceMapper;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 历史流程实例实体服务
 *
 * @version
 */
public class HistoricProcessInstanceEntityServiceImpl implements HistoricProcessInstanceEntityService
{
	/**
	 * HistoricProcessInstanceMapper
	 */
	private HistoricProcessInstanceMapper historicProcessInstanceMapper;

	/**
	 * 构造函数
	 *
	 * @param historicProcessInstanceMapper
	 */
	public HistoricProcessInstanceEntityServiceImpl(HistoricProcessInstanceMapper historicProcessInstanceMapper)
	{
		super();
		this.historicProcessInstanceMapper = historicProcessInstanceMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.save(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.update(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.delete(historicInstance.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricProcessInstance getByInstanceId(String instanceId)
	{
		return historicProcessInstanceMapper.findById(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryIdsByProcessDefinitionId(String processDefinitionId)
	{
		return historicProcessInstanceMapper.findIdsByProcessDefinitionId(processDefinitionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request)
	{
		long total = historicProcessInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricProcessInstance> list = historicProcessInstanceMapper.findPageData(rowOffset, request);

		Page<HistoricProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}

}
