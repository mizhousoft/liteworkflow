package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.mapper.ProcessDefinitionMapper;
import com.liteworkflow.engine.persistence.request.ProcessDefinitionPageRequest;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 流程定义服务
 *
 * @version
 */
public class ProcessDefinitionEntityServiceImpl implements ProcessDefinitionEntityService
{
	/**
	 * ProcessDefinitionMapper
	 */
	private ProcessDefinitionMapper processDefinitionMapper;

	/**
	 * 构造函数
	 *
	 * @param processDefinitionMapper
	 */
	public ProcessDefinitionEntityServiceImpl(ProcessDefinitionMapper processDefinitionMapper)
	{
		super();
		this.processDefinitionMapper = processDefinitionMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(ProcessDefinition processDefinition)
	{
		processDefinitionMapper.save(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(ProcessDefinition processDefinition)
	{
		processDefinitionMapper.update(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(ProcessDefinition processDefinition)
	{
		processDefinitionMapper.delete(processDefinition.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getById(String id)
	{
		return processDefinitionMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getLatestVersion(String name)
	{
		Integer ver = processDefinitionMapper.findLatestVersion(name);

		return ver;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefinition> queryByName(String name, Integer version)
	{
		return processDefinitionMapper.findByProcessName(name, version);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessDefinitionPageRequest request)
	{
		long total = processDefinitionMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ProcessDefinition> list = processDefinitionMapper.findPageData(rowOffset, request);

		Page<ProcessDefinition> page = PageBuilder.build(list, request, total);

		return page;
	}

}
