package org.snaker.engine.service.impl;

import java.util.List;

import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.mapper.HistoryOrderMapper;
import org.snaker.engine.request.HistoryOrderPageRequest;
import org.snaker.engine.service.HistoryOrderEntityService;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * HistoryOrderEntityService
 *
 * @version
 */
public class HistoryOrderEntityServiceImpl implements HistoryOrderEntityService
{
	private HistoryOrderMapper historyOrderMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(HistoryOrder order)
	{
		historyOrderMapper.save(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(HistoryOrder order)
	{
		historyOrderMapper.update(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(HistoryOrder historyOrder)
	{
		historyOrderMapper.delete(historyOrder.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryOrder getHistOrder(String orderId)
	{
		return historyOrderMapper.getHistOrder(orderId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryOrder> queryList(HistoryOrderPageRequest request)
	{
		request.setPageSize(100000);

		return historyOrderMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoryOrder> queryPageData(HistoryOrderPageRequest request)
	{
		long total = historyOrderMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoryOrder> list = historyOrderMapper.findPageData(rowOffset, request);

		Page<HistoryOrder> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置historyOrderMapper
	 * 
	 * @param historyOrderMapper
	 */
	public void setHistoryOrderMapper(HistoryOrderMapper historyOrderMapper)
	{
		this.historyOrderMapper = historyOrderMapper;
	}
}
