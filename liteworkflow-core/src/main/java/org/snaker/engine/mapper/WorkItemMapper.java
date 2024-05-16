package org.snaker.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.request.WorkItemPageRequest;

/**
 * WorkItemMapper
 *
 * @version
 */
public interface WorkItemMapper
{
	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") WorkItemPageRequest request);

	/**
	 * 查询
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<WorkItem> findPageData(@Param("rowOffset") long rowOffset, @Param("request") WorkItemPageRequest request);

	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	long countHistory(@Param("request") WorkItemPageRequest request);

	/**
	 * 查询
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<WorkItem> findHistory(@Param("rowOffset") long rowOffset, @Param("request") WorkItemPageRequest request);

}
