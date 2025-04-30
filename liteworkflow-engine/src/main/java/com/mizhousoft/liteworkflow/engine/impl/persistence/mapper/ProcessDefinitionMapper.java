package com.mizhousoft.liteworkflow.engine.impl.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.commons.mapper.PageableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;

/**
 * 流程定义持久层
 *
 * @version
 */
public interface ProcessDefinitionMapper extends PageableMapper<ProcessDefEntity, ProcessDefEntity, Integer>
{
	/**
	 * 根据流程Key查询
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	List<ProcessDefEntity> findByKey(@Param("processDefinitionKey") String processDefinitionKey, @Param("version") Integer version);

	/**
	 * 根据流程Key查询最新的版本号
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	Integer findLatestVersion(String processDefinitionKey);
}
