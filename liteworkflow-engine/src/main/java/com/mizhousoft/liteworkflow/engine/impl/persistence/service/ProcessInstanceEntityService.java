package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;

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
	int addEntity(ProcessInstanceEntity instance);

	/**
	 * 更新流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	int modifyEntity(ProcessInstanceEntity instance);

	/**
	 * 删除流程实例对象
	 * 
	 * @param instance 流程实例对象
	 */
	int deleteEntity(ProcessInstanceEntity instance);

	/**
	 * 根据流程实例id查询实例对象
	 * 
	 * @param id 活动流程实例id
	 * @return ProcessInstance 活动流程实例对象
	 */
	ProcessInstanceEntity getById(int id);

	/**
	 * 根据流程实例ID查询
	 * 
	 * @param id
	 * @return
	 */
	ProcessInstanceEntity loadById(int id);

	/**
	 * 根据ID查询
	 * 
	 * @param ids
	 * @return
	 */
	List<ProcessInstanceEntity> queryByIds(Set<Integer> ids);

	/**
	 * 根据父ID查询
	 * 
	 * @param parentId
	 * @return
	 */
	List<ProcessInstanceEntity> queryByParentId(int parentId);
}
