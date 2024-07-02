package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 流程实例持久层
 *
 * @version
 */
public interface ProcessInstanceMapper extends PageableMapper<ProcessInstance, String>
{
	/**
	 * 根据父ID查询
	 * 
	 * @param parentId
	 * @return
	 */
	List<ProcessInstance> findByParentId(String parentId);
}
