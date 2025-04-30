package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.commons.mapper.PageableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;

/**
 * 任务持久层
 *
 * @version
 */
public interface TaskMapper extends PageableMapper<TaskEntity, TaskEntity, Integer>
{
	/**
	 * 根据实例ID查询
	 * 
	 * @param instanceId
	 * @return
	 */
	List<TaskEntity> findByInstanceId(int instanceId);

	/**
	 * 根据实例ID查询
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<TaskEntity> findByInstanceIds(@Param("instanceIds") Set<Integer> instanceIds);
}
