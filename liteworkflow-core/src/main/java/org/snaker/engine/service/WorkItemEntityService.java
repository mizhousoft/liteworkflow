package org.snaker.engine.service;

import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.request.WorkItemPageRequest;

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
