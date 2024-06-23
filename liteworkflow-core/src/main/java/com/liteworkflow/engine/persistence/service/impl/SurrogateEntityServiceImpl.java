package com.liteworkflow.engine.persistence.service.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Surrogate;
import com.liteworkflow.engine.persistence.mapper.SurrogateMapper;
import com.liteworkflow.engine.persistence.request.SurrogateFindRequest;
import com.liteworkflow.engine.persistence.service.SurrogateEntityService;

/**
 * SurrogateEntityService
 *
 * @version
 */
public class SurrogateEntityServiceImpl implements SurrogateEntityService
{
	private SurrogateMapper surrogateMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(Surrogate surrogate)
	{
		surrogateMapper.save(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyEntity(Surrogate surrogate)
	{
		surrogateMapper.update(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEntity(Surrogate surrogate)
	{
		surrogateMapper.delete(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Surrogate getById(String id)
	{
		return surrogateMapper.getSurrogate(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Surrogate> queryList(SurrogateFindRequest request)
	{
		return surrogateMapper.queryList(request);
	}

	/**
	 * 设置surrogateMapper
	 * 
	 * @param surrogateMapper
	 */
	public void setSurrogateMapper(SurrogateMapper surrogateMapper)
	{
		this.surrogateMapper = surrogateMapper;
	}
}
