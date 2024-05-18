package com.liteworkflow.engine.service.impl;

import java.util.List;

import com.liteworkflow.engine.entity.CCOrder;
import com.liteworkflow.engine.entity.HistoryOrder;
import com.liteworkflow.engine.mapper.CCOrderMapper;
import com.liteworkflow.engine.request.CCOrderPageRequest;
import com.liteworkflow.engine.service.CCOrderEntityService;
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
	public List<CCOrder> getCCOrder(String orderId, String... actorIds)
	{
		return ccOrderMapper.getCCOrder(orderId, actorIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoryOrder> queryPageData(CCOrderPageRequest request)
	{
		long total = ccOrderMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoryOrder> list = ccOrderMapper.findPageData(rowOffset, request);

		Page<HistoryOrder> page = PageBuilder.build(list, request, total);

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
