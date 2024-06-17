package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.WorkItem;
import com.liteworkflow.engine.persistence.mapper.WorkItemMapper;
import com.liteworkflow.engine.persistence.request.WorkItemPageRequest;
import com.liteworkflow.engine.persistence.service.WorkItemEntityService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * WorkItemEntityService
 *
 * @version
 */
public class WorkItemEntityServiceImpl implements WorkItemEntityService
{
	private WorkItemMapper workItemMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<WorkItem> queryPageData(WorkItemPageRequest request)
	{
		long total = workItemMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<WorkItem> list = workItemMapper.findPageData(rowOffset, request);

		Page<WorkItem> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<WorkItem> queryHistoric(WorkItemPageRequest request)
	{
		long total = workItemMapper.countHistoric(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<WorkItem> list = workItemMapper.findHistoric(rowOffset, request);

		Page<WorkItem> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * 设置workItemMapper
	 * 
	 * @param workItemMapper
	 */
	public void setWorkItemMapper(WorkItemMapper workItemMapper)
	{
		this.workItemMapper = workItemMapper;
	}
}
