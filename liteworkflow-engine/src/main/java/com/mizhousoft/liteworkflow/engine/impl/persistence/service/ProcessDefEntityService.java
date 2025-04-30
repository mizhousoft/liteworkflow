package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.request.ProcessDefPageRequest;

/**
 * 流程定义服务
 *
 * @version
 */
public interface ProcessDefEntityService
{
	/**
	 * 保存流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	int addEntity(ProcessDefEntity processDefinition);

	/**
	 * 更新流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	int modifyEntity(ProcessDefEntity processDefinition);

	/**
	 * 删除流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	int deleteEntity(ProcessDefEntity processDefinition);

	/**
	 * 根据流程定义id查询流程定义对象
	 * 
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	ProcessDefEntity getById(int id);

	/**
	 * 根据流程名称查询最近的版本号
	 * 
	 * @param processDefinitionKey 流程Key
	 * @return Integer 流程定义版本号
	 */
	Integer getLatestVersion(String processDefinitionKey);

	/**
	 * 根据名称及版本号查询
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	ProcessDefEntity getByVersion(String processDefinitionKey, Integer version);

	/**
	 * 根据名称及版本号查询
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	List<ProcessDefEntity> queryByKey(String processDefinitionKey);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessDefEntity> queryPageData(ProcessDefPageRequest request);

	/**
	 * 查询最新的列表
	 * 
	 * @return
	 */
	List<ProcessDefEntity> queryLatestList();
}
