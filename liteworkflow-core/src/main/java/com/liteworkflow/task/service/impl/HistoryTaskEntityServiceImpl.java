package com.liteworkflow.task.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;

import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.task.entity.HistoryTask;
import com.liteworkflow.task.entity.HistoryTaskActor;
import com.liteworkflow.task.mapper.HistoryTaskMapper;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.task.service.HistoryTaskEntityService;

/**
 * HistoryTaskEntityService
 *
 * @version
 */
public class HistoryTaskEntityServiceImpl implements HistoryTaskEntityService
{
	private HistoryTaskMapper historyTaskMapper;

	private HistoryTaskActorEntityService historyTaskActorEntityService;

	public HistoryTaskEntityServiceImpl(SqlSessionFactory sqlSessionFactory, HistoryTaskActorEntityService historyTaskActorEntityService)
	        throws Exception
	{
		MapperFactoryBean<HistoryTaskMapper> factoryBean = new MapperFactoryBean<HistoryTaskMapper>();
		factoryBean.setMapperInterface(HistoryTaskMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		this.historyTaskMapper = (HistoryTaskMapper) factoryBean.getObject();
		this.historyTaskActorEntityService = historyTaskActorEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHistory(HistoryTask task)
	{
		historyTaskMapper.save(task);
		if (task.getActorIds() != null)
		{
			for (String actorId : task.getActorIds())
			{
				if (StringHelper.isEmpty(actorId))
					continue;

				HistoryTaskActor taskActor = new HistoryTaskActor();
				taskActor.setTaskId(task.getId());
				taskActor.setActorId(actorId);
				historyTaskActorEntityService.save(taskActor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteHistoryTask(HistoryTask historyTask)
	{
		historyTaskActorEntityService.deleteByTaskId(historyTask.getId());

		historyTaskMapper.delete(historyTask);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryTask getHistTask(String taskId)
	{
		return historyTaskMapper.getHistTask(taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTask> queryByOrderId(String orderId)
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setOrderId(orderId);

		return queryList(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HistoryTask> queryList(TaskPageRequest request)
	{
		return historyTaskMapper.findList(request);
	}
}
