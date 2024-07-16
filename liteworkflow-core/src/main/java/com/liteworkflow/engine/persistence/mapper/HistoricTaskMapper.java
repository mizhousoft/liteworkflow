package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 历史任务持久层
 *
 * @version
 */
public interface HistoricTaskMapper extends PageableMapper<HistoricTask, Integer>
{
	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> findByInstanceId(int instanceId);
}
