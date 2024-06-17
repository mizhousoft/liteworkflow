package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.mapper.HistoricProcessInstanceMapper;
import com.liteworkflow.engine.persistence.request.HistoricProcessInstPageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * HistoricProcessInstanceEntityService
 *
 * @version
 */
public class HistoricProcessInstanceEntityServiceImpl implements HistoricProcessInstanceEntityService
{
	private HistoricProcessInstanceMapper historicProcessInstanceMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.save(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.update(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(HistoricProcessInstance historicInstance)
	{
		historicProcessInstanceMapper.delete(historicInstance.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricProcessInstance getHistoricInstance(String instanceId)
	{
		return historicProcessInstanceMapper.getHistoricInstance(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricProcessInstance> queryList(HistoricProcessInstPageRequest request)
	{
		request.setPageSize(100000);

		return historicProcessInstanceMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(HistoricProcessInstPageRequest request)
	{
		long total = historicProcessInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricProcessInstance> list = historicProcessInstanceMapper.findPageData(rowOffset, request);

		Page<HistoricProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}

	public void setHistoricProcessInstanceMapper(HistoricProcessInstanceMapper historicProcessInstanceMapper)
	{
		this.historicProcessInstanceMapper = historicProcessInstanceMapper;
	}

}
