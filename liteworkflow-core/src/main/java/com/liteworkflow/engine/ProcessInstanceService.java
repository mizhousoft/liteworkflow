package com.liteworkflow.engine;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程实例业务类
 * 
 * @version
 */
public interface ProcessInstanceService
{
	/**
	 * 根据流程实例ID获取流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	ProcessInstance getInstance(int instanceId);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request);
}
