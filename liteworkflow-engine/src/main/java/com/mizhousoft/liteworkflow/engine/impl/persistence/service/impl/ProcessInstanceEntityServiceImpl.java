package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.ProcessInstanceMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;

/**
 * ProcessInstanceEntityService
 *
 * @version
 */
public class ProcessInstanceEntityServiceImpl implements ProcessInstanceEntityService
{
	/**
	 * ProcessInstanceMapper
	 */
	private ProcessInstanceMapper processInstanceMapper;

	/**
	 * 构造函数
	 *
	 * @param processInstanceMapper
	 */
	public ProcessInstanceEntityServiceImpl(ProcessInstanceMapper processInstanceMapper)
	{
		super();
		this.processInstanceMapper = processInstanceMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addEntity(ProcessInstanceEntity instance)
	{
		return processInstanceMapper.save(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(ProcessInstanceEntity instance)
	{
		return processInstanceMapper.update(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteEntity(ProcessInstanceEntity instance)
	{
		return processInstanceMapper.delete(instance.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceEntity getById(int id)
	{
		return processInstanceMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceEntity loadById(int id)
	{
		ProcessInstanceEntity processInstance = getById(id);

		Assert.notNull(processInstance, "ProcessInstance not found, id is " + id);

		return processInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessInstanceEntity> queryByIds(Set<Integer> ids)
	{
		if (CollectionUtils.isEmpty(ids))
		{
			return Collections.emptyList();
		}

		return processInstanceMapper.findByIds(ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessInstanceEntity> queryByParentId(int parentId)
	{
		return processInstanceMapper.findByParentId(parentId);
	}

}
