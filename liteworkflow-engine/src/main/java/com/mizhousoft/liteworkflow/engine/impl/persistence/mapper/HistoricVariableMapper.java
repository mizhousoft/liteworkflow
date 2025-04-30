package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.commons.mapper.CrudMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricVariable;

/**
 * 历史变量持久层
 *
 * @version
 */
public interface HistoricVariableMapper extends CrudMapper<HistoricVariable, Integer>
{
	/**
	 * 根据流程实例ID删除
	 * 
	 * @param instanceId
	 */
	int deleteByInstanceId(int instanceId);

	/**
	 * 根据任务ID删除
	 * 
	 * @param instanceId
	 * @param taskId
	 * @return
	 */
	int deleteByTaskId(@Param("instanceId") int instanceId, @Param("taskId") int taskId);

	/**
	 * 根据任务ID查询
	 * 
	 * @param instanceId
	 * @param taskId
	 * @return
	 */
	List<HistoricVariable> findByTaskId(@Param("instanceId") int instanceId, @Param("taskId") int taskId);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<HistoricVariable> findByInstanceIds(@Param("instanceIds") Set<Integer> instanceIds);
}
