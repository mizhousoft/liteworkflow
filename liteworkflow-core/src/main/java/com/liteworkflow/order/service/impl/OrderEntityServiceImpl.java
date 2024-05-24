package com.liteworkflow.order.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;

import com.liteworkflow.order.entity.HistoryOrder;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.order.mapper.OrderMapper;
import com.liteworkflow.order.request.OrderPageRequest;
import com.liteworkflow.order.service.HistoryOrderEntityService;
import com.liteworkflow.order.service.OrderEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * OrderEntityService
 *
 * @version
 */
public class OrderEntityServiceImpl implements OrderEntityService
{
	/**
	 * 状态；活动状态
	 */
	public static final Integer STATE_ACTIVE = 1;

	/**
	 * 状态：结束状态
	 */
	public static final Integer STATE_FINISH = 0;

	/**
	 * 状态：终止状态
	 */
	public static final Integer STATE_TERMINATION = 2;

	private OrderMapper orderMapper;

	private HistoryOrderEntityService historyOrderEntityService;

	public OrderEntityServiceImpl(SqlSessionFactory sqlSessionFactory, HistoryOrderEntityService historyOrderEntityService) throws Exception
	{
		MapperFactoryBean<OrderMapper> factoryBean = new MapperFactoryBean<OrderMapper>();
		factoryBean.setMapperInterface(OrderMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		this.orderMapper = (OrderMapper) factoryBean.getObject();
		this.historyOrderEntityService = historyOrderEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrder(Order order)
	{
		orderMapper.save(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrderAndHistory(Order order)
	{
		HistoryOrder history = new HistoryOrder(order);
		history.setOrderState(STATE_ACTIVE);
		orderMapper.save(order);
		historyOrderEntityService.save(history);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrder(Order order)
	{
		orderMapper.update(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteOrder(Order order)
	{
		orderMapper.delete(order.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Order getOrder(String orderId)
	{
		return orderMapper.getOrder(orderId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrderVariable(Order order)
	{
		updateOrder(order);
		HistoryOrder hist = historyOrderEntityService.getHistOrder(order.getId());
		hist.setVariable(order.getVariable());
		historyOrderEntityService.update(hist);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Order> queryList(OrderPageRequest request)
	{
		request.setPageSize(100000);

		return orderMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Order> queryPageData(OrderPageRequest request)
	{
		long total = orderMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<Order> list = orderMapper.findPageData(rowOffset, request);

		Page<Order> page = PageBuilder.build(list, request, total);

		return page;
	}
}
