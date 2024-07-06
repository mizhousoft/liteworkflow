package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.Task;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 任务持久层
 *
 * @version
 */
public interface TaskMapper extends PageableMapper<Task, String>
{
	/**
	 * 根据实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<Task> findByInstanceId(String instanceId);
}
