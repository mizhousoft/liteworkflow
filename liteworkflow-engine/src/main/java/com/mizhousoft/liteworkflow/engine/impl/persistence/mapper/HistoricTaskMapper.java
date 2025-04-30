package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.List;

import com.mizhousoft.commons.mapper.PageableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricTaskEntity;

/**
 * 历史任务持久层
 *
 * @version
 */
public interface HistoricTaskMapper extends PageableMapper<HistoricTaskEntity, HistoricTaskEntity, Integer>
{
	/**
	 * 根据流程实例ID删除
	 * 
	 * @param instanceId
	 * @return
	 */
	int deleteByInstanceId(int instanceId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTaskEntity> findByInstanceId(int instanceId);
}
