package com.liteworkflow.engine.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 流程定义持久层
 *
 * @version
 */
public interface ProcessDefinitionMapper extends PageableMapper<ProcessDefinition, Integer>
{
	/**
	 * 根据流程Key查询
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	List<ProcessDefinition> findByKey(@Param("processDefinitionKey") String processDefinitionKey, @Param("version") Integer version);

	/**
	 * 根据流程Key查询最新的版本号
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	Integer findLatestVersion(String processDefinitionKey);
}
