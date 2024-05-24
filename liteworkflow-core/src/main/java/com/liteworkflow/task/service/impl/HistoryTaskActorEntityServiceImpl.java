package com.liteworkflow.task.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;

import com.liteworkflow.task.entity.HistoryTaskActor;
import com.liteworkflow.task.mapper.HistoryTaskActorMapper;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;

/**
 * HistoryTaskActorEntityService
 *
 * @version
 */
public class HistoryTaskActorEntityServiceImpl implements HistoryTaskActorEntityService
{
	private HistoryTaskActorMapper historyTaskActorMapper;

	public HistoryTaskActorEntityServiceImpl(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<HistoryTaskActorMapper> factoryBean = new MapperFactoryBean<HistoryTaskActorMapper>();
		factoryBean.setMapperInterface(HistoryTaskActorMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		this.historyTaskActorMapper = (HistoryTaskActorMapper) factoryBean.getObject();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(HistoryTaskActor taskActor)
	{
		historyTaskActorMapper.save(taskActor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByTaskId(String taskId)
	{
		historyTaskActorMapper.deleteByTaskId(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTaskActor> getHistTaskActorsByTaskId(String taskId)
	{
		return historyTaskActorMapper.getHistTaskActorsByTaskId(taskId);
	}

}
