package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 历史任务持久层
 *
 * @version
 */
public interface HistoricTaskMapper extends PageableMapper<HistoricTask, String>
{
	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> findByInstanceId(String instanceId);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<HistoricTask> findList(TaskPageRequest request);
}
