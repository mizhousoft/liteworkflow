package com.mizhousoft.liteworkflow.engine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;

/**
 * 流程实例业务类
 * 
 * @version
 */
public interface ProcessInstanceService
{
	/**
	 * 根据流程实例ID获取流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	ProcessInstance getInstance(int instanceId);

	/**
	 * 根据流程实例ID获取流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	ProcessInstance loadInstance(int instanceId);

	/**
	 * 查询列表
	 * 
	 * @param instanceIds
	 * @return
	 */
	List<ProcessInstance> queryInstanceList(Set<Integer> instanceIds);

	/**
	 * 查询列表
	 * 
	 * @param instanceIds
	 * @return
	 */
	Map<Integer, ProcessInstance> queryInstanceMap(Set<Integer> instanceIds);
}
