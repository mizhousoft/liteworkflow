package com.liteworkflow.workitem.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;

import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.mapper.WorkItemMapper;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.liteworkflow.workitem.service.WorkItemEntityService;
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

	public WorkItemEntityServiceImpl(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<WorkItemMapper> factoryBean = new MapperFactoryBean<WorkItemMapper>();
		factoryBean.setMapperInterface(WorkItemMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		this.workItemMapper = (WorkItemMapper) factoryBean.getObject();
	}

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
	public Page<WorkItem> queryHistory(WorkItemPageRequest request)
	{
		long total = workItemMapper.countHistory(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<WorkItem> list = workItemMapper.findHistory(rowOffset, request);

		Page<WorkItem> page = PageBuilder.build(list, request, total);

		return page;
	}

}
