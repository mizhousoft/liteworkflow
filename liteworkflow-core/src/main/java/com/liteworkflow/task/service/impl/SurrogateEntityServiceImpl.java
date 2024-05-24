package com.liteworkflow.task.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;

import com.liteworkflow.task.entity.Surrogate;
import com.liteworkflow.task.mapper.SurrogateMapper;
import com.liteworkflow.task.request.SurrogateFindRequest;
import com.liteworkflow.task.service.SurrogateEntityService;

/**
 * SurrogateEntityService
 *
 * @version
 */
public class SurrogateEntityServiceImpl implements SurrogateEntityService
{
	private SurrogateMapper surrogateMapper;

	public SurrogateEntityServiceImpl(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<SurrogateMapper> factoryBean = new MapperFactoryBean<SurrogateMapper>();
		factoryBean.setMapperInterface(SurrogateMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		this.surrogateMapper = (SurrogateMapper) factoryBean.getObject();
	}

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
}
