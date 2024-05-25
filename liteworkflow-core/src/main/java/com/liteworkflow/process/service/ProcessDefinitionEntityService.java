package com.liteworkflow.process.service;

import java.util.List;

import com.liteworkflow.process.entity.ProcessDefinition;
import com.liteworkflow.process.request.ProcessPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * ProcessDefinitionEntityService
 *
 * @version
 */
public interface ProcessDefinitionEntityService
{
	/**
	 * 保存流程定义对象
	 * 
	 * @param process 流程定义对象
	 */
	void save(ProcessDefinition process);

	/**
	 * 更新流程定义对象
	 * 
	 * @param process 流程定义对象
	 */
	void update(ProcessDefinition process);

	/**
	 * 更新流程定义类别
	 * 
	 * @param type 类别
	 * @since 1.5
	 */
	void updateProcessType(String id, String type);

	/**
	 * 删除流程定义对象
	 * 
	 * @param process 流程定义对象
	 */
	void delete(ProcessDefinition process);

	/**
	 * 根据流程定义id查询流程定义对象
	 * 
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	ProcessDefinition getProcess(String id);

	/**
	 * 根据流程名称查询最近的版本号
	 * 
	 * @param name 流程名称
	 * @return Integer 流程定义版本号
	 */
	Integer getLatestProcessVersion(String name);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<ProcessDefinition> queryList(ProcessPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessDefinition> queryPageData(ProcessPageRequest request);
}
