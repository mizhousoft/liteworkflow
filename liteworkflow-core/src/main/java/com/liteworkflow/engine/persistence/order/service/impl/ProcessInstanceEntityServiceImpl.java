package com.liteworkflow.engine.persistence.order.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.order.mapper.ProcessInstanceMapper;
import com.liteworkflow.engine.persistence.order.request.ProcessInstPageRequest;
import com.liteworkflow.engine.persistence.order.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.order.service.ProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * OrderEntityService
 *
 * @version
 */
public class ProcessInstanceEntityServiceImpl implements ProcessInstanceEntityService
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

	private ProcessInstanceMapper processInstanceMapper;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrder(ProcessInstance order)
	{
		processInstanceMapper.save(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrderAndHistory(ProcessInstance order)
	{
		HistoricProcessInstance history = new HistoricProcessInstance(order);
		history.setOrderState(STATE_ACTIVE);
		processInstanceMapper.save(order);
		historicProcessInstanceEntityService.save(history);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrder(ProcessInstance order)
	{
		processInstanceMapper.update(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteOrder(ProcessInstance order)
	{
		processInstanceMapper.delete(order.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance getOrder(String orderId)
	{
		return processInstanceMapper.getOrder(orderId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrderVariable(ProcessInstance order)
	{
		updateOrder(order);
		HistoricProcessInstance hist = historicProcessInstanceEntityService.getHistOrder(order.getId());
		hist.setVariable(order.getVariable());
		historicProcessInstanceEntityService.update(hist);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessInstance> queryList(ProcessInstPageRequest request)
	{
		request.setPageSize(100000);

		return processInstanceMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstPageRequest request)
	{
		long total = processInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ProcessInstance> list = processInstanceMapper.findPageData(rowOffset, request);

		Page<ProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置processInstanceMapper
	 * 
	 * @param processInstanceMapper
	 */
	public void setProcessInstanceMapper(ProcessInstanceMapper processInstanceMapper)
	{
		this.processInstanceMapper = processInstanceMapper;
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
}
