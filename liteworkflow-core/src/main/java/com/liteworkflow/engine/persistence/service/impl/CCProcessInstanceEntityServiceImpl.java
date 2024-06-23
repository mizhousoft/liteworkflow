package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.CCProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.mapper.CCProcessInstanceMapper;
import com.liteworkflow.engine.persistence.request.CCInstancePageRequest;
import com.liteworkflow.engine.persistence.service.CCProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * CCProcessInstanceEntityService
 *
 * @version
 */
public class CCProcessInstanceEntityServiceImpl implements CCProcessInstanceEntityService
{
	private CCProcessInstanceMapper ccProcessInstanceMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(CCProcessInstance ccInstance)
	{
		ccProcessInstanceMapper.save(ccInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(CCProcessInstance ccInstance)
	{
		ccProcessInstanceMapper.update(ccInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(CCProcessInstance ccInstance)
	{
		ccProcessInstanceMapper.delete(ccInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CCProcessInstance> queryList(String instanceId, String... actorIds)
	{
		return ccProcessInstanceMapper.findByInstanceId(instanceId, actorIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(CCInstancePageRequest request)
	{
		long total = ccProcessInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricProcessInstance> list = ccProcessInstanceMapper.findPageData(rowOffset, request);

		Page<HistoricProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置ccProcessInstanceMapper
	 * 
	 * @param ccProcessInstanceMapper
	 */
	public void setCcProcessInstanceMapper(CCProcessInstanceMapper ccProcessInstanceMapper)
	{
		this.ccProcessInstanceMapper = ccProcessInstanceMapper;
	}
}
