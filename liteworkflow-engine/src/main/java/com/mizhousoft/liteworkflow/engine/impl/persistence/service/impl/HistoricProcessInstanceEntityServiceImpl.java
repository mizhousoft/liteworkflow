package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricProcessInstanceMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.request.HistoricInstancePageRequest;

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
	public int addEntity(HistoricInstanceEntity historicInstance)
	{
		return historicProcessInstanceMapper.save(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(HistoricInstanceEntity historicInstance)
	{
		return historicProcessInstanceMapper.update(historicInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteEntity(HistoricInstanceEntity historicInstance)
	{
		return historicProcessInstanceMapper.delete(historicInstance.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricInstanceEntity getById(int id)
	{
		return historicProcessInstanceMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricInstanceEntity loadById(int id)
	{
		HistoricInstanceEntity entity = getById(id);
		if (null == entity)
		{
			throw new WorkFlowException("HistoricInstanceEntity not found, id is " + id);
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoricInstanceEntity> queryByIds(Set<Integer> ids)
	{
		if (CollectionUtils.isEmpty(ids))
		{
			return Collections.emptyList();
		}

		return historicProcessInstanceMapper.findByIds(ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Integer> queryIdsByProcessDefinitionId(int processDefinitionId)
	{
		return historicProcessInstanceMapper.findIdsByProcessDefinitionId(processDefinitionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<HistoricInstanceEntity> queryPageData(HistoricInstancePageRequest request)
	{
		long total = historicProcessInstanceMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<HistoricInstanceEntity> list = historicProcessInstanceMapper.findPageData(rowOffset, request);

		Page<HistoricInstanceEntity> page = PageBuilder.build(list, request, total);

		return page;
	}

}
