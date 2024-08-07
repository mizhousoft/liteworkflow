package com.liteworkflow.engine.persistence.service;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.ProcessDefinitionPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义服务
 *
 * @version
 */
public interface ProcessDefinitionEntityService
{
	/**
	 * 保存流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	void addEntity(ProcessDefinition processDefinition);

	/**
	 * 更新流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	void modifyEntity(ProcessDefinition processDefinition);

	/**
	 * 删除流程定义对象
	 * 
	 * @param processDefinition 流程定义对象
	 */
	void deleteEntity(ProcessDefinition processDefinition);

	/**
	 * 根据流程定义id查询流程定义对象
	 * 
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	ProcessDefinition getById(int id);

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
	List<ProcessDefinition> queryByKey(String processDefinitionKey, Integer version);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessDefinition> queryPageData(ProcessDefinitionPageRequest request);
}
