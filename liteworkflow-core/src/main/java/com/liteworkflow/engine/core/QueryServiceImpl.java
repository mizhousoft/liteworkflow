package com.liteworkflow.engine.core;

import com.liteworkflow.engine.QueryService;
import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.liteworkflow.workitem.service.WorkItemEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 查询服务实现类
 * 
 * @author yuqs
 * @since 1.0
 */
public class QueryServiceImpl extends AccessService implements QueryService
{
	private WorkItemEntityService workItemEntityService;

	@Override
	public Page<WorkItem> getWorkItems(WorkItemPageRequest request)
	{
		return workItemEntityService.queryPageData(request);
	}

	/**
	 * 设置workItemEntityService
	 * 
	 * @param workItemEntityService
	 */
	public void setWorkItemEntityService(WorkItemEntityService workItemEntityService)
	{
		this.workItemEntityService = workItemEntityService;
	}
}
