package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
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
	void addEntity(ProcessInstance instance);

	/**
	 * 更新流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	void modifyEntity(ProcessInstance instance);

	/**
	 * 更新实例变量（包括历史实例表）
	 * 
	 * @param instance 实例对象
	 */
	void modifyVariable(ProcessInstance instance);

	/**
	 * 删除流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	void deleteEntity(ProcessInstance instance);

	/**
	 * 根据流程实例id查询实例对象
	 * 
	 * @param id 活动流程实例id
	 * @return ProcessInstance 活动流程实例对象
	 */
	ProcessInstance getById(String id);

	/**
	 * 根据父ID查询
	 * 
	 * @param parentId
	 * @return
	 */
	List<ProcessInstance> queryByParentId(String parentId);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request);
}
