package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.List;

import com.mizhousoft.commons.mapper.PageableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;

/**
 * 流程实例持久层
 *
 * @version
 */
public interface ProcessInstanceMapper extends PageableMapper<ProcessInstanceEntity, ProcessInstanceEntity, Integer>
{
	/**
	 * 根据父ID查询
	 * 
	 * @param parentId
	 * @return
	 */
	List<ProcessInstanceEntity> findByParentId(int parentId);
}
