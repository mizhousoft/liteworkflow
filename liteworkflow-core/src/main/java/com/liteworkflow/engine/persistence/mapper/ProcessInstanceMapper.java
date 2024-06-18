package com.liteworkflow.engine.persistence.mapper;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * ProcessInstanceMapper
 *
 * @version
 */
public interface ProcessInstanceMapper extends PageableMapper<ProcessInstance, String>
{
	/**
	 * 根据流程实例id查询实例对象
	 * 
	 * @param instanceId 活动流程实例id
	 * @return ProcessInstance 活动流程实例对象
	 */
	ProcessInstance getInstance(String instanceId);

	/**
	 * 更新实例变量（包括历史实例表）
	 * 
	 * @param instance 实例对象
	 */
	void updateVariable(ProcessInstance instance);
}
