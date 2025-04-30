package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricVariable;

/**
 * 历史变量实体服务
 *
 * @version
 */
public interface HistoricVariableEntityService
{
	/**
	 * 新增历史变量
	 *
	 * @param entity
	 * @return
	 */
	int addEntity(HistoricVariable entity);

	/**
	 * 修改历史变量
	 *
	 * @param entity
	 * @return
	 */
	int modifyEntity(HistoricVariable entity);

	/**
	 * 更新变量
	 * 
	 * @param instanceId
	 * @param taskId
	 * @param variableMap
	 */
	void updateVariables(int instanceId, int taskId, Map<String, Object> variableMap);

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(int id);

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
	int deleteByTaskId(int instanceId, int taskId);

	/**
	 * 根据任务ID查询
	 * 
	 * @param taskId
	 * @return
	 */
	Map<String, Object> queryMapByTaskId(int instanceId, int taskId);

	/**
	 * 根据实例ID查询
	 * 
	 * @param instanceIds
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> queryMapByInstanceIds(Set<Integer> instanceIds);

	/**
	 * 根据任务ID查询
	 * 
	 * @param instanceId
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> queryMapByTaskIds(int instanceId, Set<Integer> taskIds);

	/**
	 * 根据任务ID查询
	 * 
	 * @param instanceIds
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> queryMapByTaskIds(Set<Integer> instanceIds, Set<Integer> taskIds);
}
