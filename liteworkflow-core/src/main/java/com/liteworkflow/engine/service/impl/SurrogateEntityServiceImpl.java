package com.liteworkflow.engine.service.impl;

import java.util.List;

import com.liteworkflow.engine.entity.Surrogate;
import com.liteworkflow.engine.mapper.SurrogateMapper;
import com.liteworkflow.engine.request.SurrogateFindRequest;
import com.liteworkflow.engine.service.SurrogateEntityService;

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
	public void save(Surrogate surrogate)
	{
		surrogateMapper.save(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Surrogate surrogate)
	{
		surrogateMapper.update(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Surrogate surrogate)
	{
		surrogateMapper.delete(surrogate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Surrogate getSurrogate(String id)
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
