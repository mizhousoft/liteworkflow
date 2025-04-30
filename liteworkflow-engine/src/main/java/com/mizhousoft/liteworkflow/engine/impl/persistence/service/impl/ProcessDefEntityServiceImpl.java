package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.List;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.ProcessDefinitionMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;
import com.mizhousoft.liteworkflow.engine.request.ProcessDefPageRequest;

/**
 * 流程定义服务
 *
 * @version
 */
public class ProcessDefEntityServiceImpl implements ProcessDefEntityService
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
	public ProcessDefEntityServiceImpl(ProcessDefinitionMapper processDefinitionMapper)
	{
		super();
		this.processDefinitionMapper = processDefinitionMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addEntity(ProcessDefEntity processDefinition)
	{
		return processDefinitionMapper.save(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(ProcessDefEntity processDefinition)
	{
		return processDefinitionMapper.update(processDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteEntity(ProcessDefEntity processDefinition)
	{
		return processDefinitionMapper.delete(processDefinition.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity getById(int id)
	{
		return processDefinitionMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getLatestVersion(String processDefinitionKey)
	{
		Integer ver = processDefinitionMapper.findLatestVersion(processDefinitionKey);

		return ver;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity getByVersion(String processDefinitionKey, Integer version)
	{
		List<ProcessDefEntity> list = processDefinitionMapper.findByKey(processDefinitionKey, version);
		if (!list.isEmpty())
		{
			return list.get(0);
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefEntity> queryByKey(String processDefinitionKey)
	{
		return processDefinitionMapper.findByKey(processDefinitionKey, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefEntity> queryPageData(ProcessDefPageRequest request)
	{
		long total = processDefinitionMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ProcessDefEntity> list = processDefinitionMapper.findPageData(rowOffset, request);

		Page<ProcessDefEntity> page = PageBuilder.build(list, request, total);

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefEntity> queryLatestList()
	{
		ProcessDefPageRequest request = new ProcessDefPageRequest();
		request.setPageSize(200);

		List<ProcessDefEntity> list = processDefinitionMapper.findPageData(0, request);

		return list;
	}
}
