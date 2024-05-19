package com.liteworkflow.process.service.impl;

import java.util.List;

import com.liteworkflow.process.entity.Process;
import com.liteworkflow.process.mapper.ProcessMapper;
import com.liteworkflow.process.request.ProcessPageRequest;
import com.liteworkflow.process.service.ProcessEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * ProcessEntityService
 *
 * @version
 */
public class ProcessEntityServiceImpl implements ProcessEntityService
{
	private ProcessMapper processMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Process process)
	{
		processMapper.save(process);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Process process)
	{
		processMapper.update(process);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateProcessType(String id, String type)
	{
		processMapper.updateProcessType(id, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Process process)
	{
		processMapper.delete(process.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Process getProcess(String id)
	{
		return processMapper.getProcess(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getLatestProcessVersion(String name)
	{
		Integer ver = processMapper.getLatestProcessVersion(name);

		return (ver != null ? ver.intValue() : -1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Process> queryList(ProcessPageRequest request)
	{
		request.setPageSize(10000);

		return processMapper.findPageData(0, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Process> queryPageData(ProcessPageRequest request)
	{
		long total = processMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<Process> list = processMapper.findPageData(rowOffset, request);

		Page<Process> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置processMapper
	 * 
	 * @param processMapper
	 */
	public void setProcessMapper(ProcessMapper processMapper)
	{
		this.processMapper = processMapper;
	}

}
