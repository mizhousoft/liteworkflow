package com.liteworkflow.process.service.impl;

import java.util.List;

import com.liteworkflow.process.entity.ProcessDefinition;
import com.liteworkflow.process.mapper.ProcessDefinitionMapper;
import com.liteworkflow.process.request.ProcessPageRequest;
import com.liteworkflow.process.service.ProcessDefinitionEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * ProcessDefinitionEntityService
 *
 * @version
 */
public class ProcessDefinitionEntityServiceImpl implements ProcessDefinitionEntityService
{
	private ProcessDefinitionMapper processDefinitionMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(ProcessDefinition process)
	{
		processDefinitionMapper.save(process);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(ProcessDefinition process)
	{
		processDefinitionMapper.update(process);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateProcessType(String id, String type)
	{
		processDefinitionMapper.updateProcessType(id, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(ProcessDefinition process)
	{
		processDefinitionMapper.delete(process.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getProcess(String id)
	{
		return processDefinitionMapper.getProcess(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getLatestProcessVersion(String name)
	{
		Integer ver = processDefinitionMapper.getLatestProcessVersion(name);

		return (ver != null ? ver.intValue() : -1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefinition> queryList(ProcessPageRequest request)
	{
		request.setPageSize(10000);

		return processDefinitionMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessPageRequest request)
	{
		long total = processDefinitionMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ProcessDefinition> list = processDefinitionMapper.findPageData(rowOffset, request);

		Page<ProcessDefinition> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置processDefinitionMapper
	 * 
	 * @param processDefinitionMapper
	 */
	public void setprocessDefinitionMapper(ProcessDefinitionMapper processDefinitionMapper)
	{
		this.processDefinitionMapper = processDefinitionMapper;
	}
}
