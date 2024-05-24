package com.liteworkflow.engine;

import com.liteworkflow.workitem.entity.WorkItem;
import com.liteworkflow.workitem.request.WorkItemPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程相关的查询服务
 * 
 * @author yuqs
 * @since 1.0
 */
public interface QueryService
{
	/**
	 * 根据filter分页查询工作项（包含process、order、task三个实体的字段集合）
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 活动工作项集合
	 */
	Page<WorkItem> getWorkItems(WorkItemPageRequest request);
}
