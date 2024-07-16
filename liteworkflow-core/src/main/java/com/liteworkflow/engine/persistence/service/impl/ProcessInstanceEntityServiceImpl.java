package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.mapper.ProcessInstanceMapper;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * ProcessInstanceEntityService
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

	/**
	 * ProcessInstanceMapper
	 */
	private ProcessInstanceMapper processInstanceMapper;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * 构造函数
	 *
	 * @param processInstanceMapper
	 * @param historicProcessInstanceEntityService
	 */
	public ProcessInstanceEntityServiceImpl(ProcessInstanceMapper processInstanceMapper,
	        HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		super();
		this.processInstanceMapper = processInstanceMapper;
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(ProcessInstance instance)
	{
		processInstanceMapper.save(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(ProcessInstance instance)
	{
		processInstanceMapper.update(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyVariable(ProcessInstance instance)
	{
		modifyEntity(instance);

		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getByInstanceId(instance.getId());
		historicInstance.setVariable(instance.getVariable());
		historicProcessInstanceEntityService.modifyEntity(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(ProcessInstance instance)
	{
		processInstanceMapper.delete(instance.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance getById(int id)
	{
		ProcessInstance processInstance = processInstanceMapper.findById(id);

		return processInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessInstance> queryByParentId(int parentId)
	{
		return processInstanceMapper.findByParentId(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request)
	{
		long total = processInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ProcessInstance> list = processInstanceMapper.findPageData(rowOffset, request);

		Page<ProcessInstance> page = PageBuilder.build(list, request, total);

		return page;
	}
}
