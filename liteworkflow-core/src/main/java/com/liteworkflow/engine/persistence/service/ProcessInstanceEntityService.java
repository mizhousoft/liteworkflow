package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * ProcessInstanceEntityService
 *
 * @version
 */
public interface ProcessInstanceEntityService
{
	/**
	 * 保存流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	void saveInstance(ProcessInstance instance);

	/**
	 * 保存流程实例
	 * 
	 * @param instance 流程实例对象
	 */
	void saveInstanceAndHistoric(ProcessInstance instance);

	/**
	 * 更新流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	void updateInstance(ProcessInstance instance);

	/**
	 * 删除流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	void deleteInstance(ProcessInstance instance);

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

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<ProcessInstance> queryList(ProcessInstPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessInstance> queryPageData(ProcessInstPageRequest request);
}
