package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.CCOrder;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.mapper.CCOrderMapper;
import com.liteworkflow.engine.persistence.request.CCOrderPageRequest;
import com.liteworkflow.engine.persistence.service.CCOrderEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * CCOrderEntityService
 *
 * @version
 */
public class CCOrderEntityServiceImpl implements CCOrderEntityService
{
	private CCOrderMapper ccOrderMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(CCOrder ccorder)
	{
		ccOrderMapper.save(ccorder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(CCOrder ccorder)
	{
		ccOrderMapper.update(ccorder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(CCOrder ccorder)
	{
		ccOrderMapper.deleteCCOrder(ccorder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CCOrder> getCCOrder(String instanceId, String... actorIds)
	{
		return ccOrderMapper.getCCOrder(instanceId, actorIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricProcessInstance> queryPageData(CCOrderPageRequest request)
	{
		long total = ccOrderMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricProcessInstance> list = ccOrderMapper.findPageData(rowOffset, request);

		Page<HistoricProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置ccOrderMapper
	 * 
	 * @param ccOrderMapper
	 */
	public void setCcOrderMapper(CCOrderMapper ccOrderMapper)
	{
		this.ccOrderMapper = ccOrderMapper;
	}
}
