package com.liteworkflow.engine.service;

import java.util.List;

import com.liteworkflow.engine.entity.Process;
import com.liteworkflow.engine.request.ProcessPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * ProcessEntityService
 *
 * @version
 */
public interface ProcessEntityService
{
	/**
	 * 保存流程定义对象
	 * 
	 * @param process 流程定义对象
	 */
	void save(Process process);

	/**
	 * 更新流程定义对象
	 * 
	 * @param process 流程定义对象
	 */
	void update(Process process);

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
	void delete(Process process);

	/**
	 * 根据流程定义id查询流程定义对象
	 * 
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	Process getProcess(String id);

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
	List<Process> queryList(ProcessPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<Process> queryPageData(ProcessPageRequest request);
}
