package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liteworkflow.engine.persistence.entity.CCProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.request.CCInstancePageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * CCProcessInstanceMapper
 *
 * @version
 */
public interface CCProcessInstanceMapper extends CrudMapper<CCProcessInstance, String>
{
	/**
	 * 删除抄送记录
	 * 
	 * @param ccInstance 抄送实体对象
	 */
	void delete(CCProcessInstance ccInstance);

	/**
	 * 根据流程实例id、参与者id获取抄送记录
	 * 
	 * @param instanceId 活动流程实例id
	 * @param actorIds 参与者id
	 * @return 传送记录列表
	 */
	List<CCProcessInstance> findByInstanceId(@Param("instanceId") String instanceId, @Param("actorIds") String... actorIds);

	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	long countTotal(@Param("request") CCInstancePageRequest request);

	/**
	 * 查询
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<HistoricProcessInstance> findPageData(@Param("rowOffset") long rowOffset, @Param("request") CCInstancePageRequest request);
}
