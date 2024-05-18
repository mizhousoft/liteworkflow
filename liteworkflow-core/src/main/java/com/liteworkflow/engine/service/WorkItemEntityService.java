package com.liteworkflow.engine.service;

import com.liteworkflow.engine.entity.WorkItem;
import com.liteworkflow.engine.request.WorkItemPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * WorkItemEntityService
 *
 * @version
 */
public interface WorkItemEntityService
{

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<WorkItem> queryPageData(WorkItemPageRequest request);

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	Page<WorkItem> queryHistory(WorkItemPageRequest request);
}
