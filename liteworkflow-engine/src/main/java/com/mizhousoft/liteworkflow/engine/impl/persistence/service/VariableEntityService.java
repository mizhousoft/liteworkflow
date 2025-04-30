package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;

/**
 * 变量实体服务
 *
 * @version
 */
public interface VariableEntityService
{
	/**
	 * 新增变量
	 * 
	 * @param instanceId
	 * @param taskId
	 * @param variableMap
	 * @return
	 */
	List<Variable> addVariables(int instanceId, int taskId, Map<String, Object> variableMap);

	/**
	 * 更新变量
	 * 
	 * @param instanceId
	 * @param taskId
	 * @param variableMap
	 * @return
	 */
	Map<String, Object> updateVariables(int instanceId, int taskId, Map<String, Object> variableMap);

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
	 * @param containTaskVar
	 * @return
	 */
	int deleteByInstanceId(int instanceId, boolean containTaskVar);

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
	 * @param instanceId
	 * @param taskId
	 * @return
	 */
	List<Variable> queryByTaskId(int instanceId, int taskId);

	/**
	 * 根据任务ID查询
	 * 
	 * @param taskId
	 * @return
	 */
	Map<String, Object> queryMapByTaskId(int instanceId, int taskId);

	/**
	 * 根据任务ID查询
	 * 
	 * @param instanceIds
	 * @param taskIds
	 * @return
	 */
	Map<Integer, Map<String, Object>> queryMapByTaskIds(Set<Integer> instanceIds, Set<Integer> taskIds);
}
