package org.snaker.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.snaker.engine.entity.CCOrder;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.request.CCOrderPageRequest;

import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * CCOrderMapper
 *
 * @version
 */
public interface CCOrderMapper extends CrudMapper<CCOrder, String>
{
	/**
	 * 删除抄送记录
	 * 
	 * @param ccorder 抄送实体对象
	 */
	void deleteCCOrder(CCOrder ccorder);

	/**
	 * 根据流程实例id、参与者id获取抄送记录
	 * 
	 * @param orderId 活动流程实例id
	 * @param actorIds 参与者id
	 * @return 传送记录列表
	 */
	List<CCOrder> getCCOrder(@Param("orderId") String orderId, @Param("actorIds") String... actorIds);

	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") CCOrderPageRequest request);

	/**
	 * 查询
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<HistoryOrder> findPageData(@Param("rowOffset") long rowOffset, @Param("request") CCOrderPageRequest request);
}
