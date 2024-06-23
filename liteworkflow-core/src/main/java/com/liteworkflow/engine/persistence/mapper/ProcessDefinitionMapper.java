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
public interface ProcessDefinitionMapper extends PageableMapper<ProcessDefinition, String>
{
	/**
	 * 更新流程定义类别
	 * 
	 * @param type 类别
	 * @since 1.5
	 */
	void updateProcessType(@Param("id") String id, @Param("type") String type);

	/**
	 * 根据流程名称查询
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	List<ProcessDefinition> findByProcessName(@Param("name") String name, @Param("version") Integer version);

	/**
	 * 根据流程名称查询最近的版本号
	 * 
	 * @param name 流程名称
	 * @return Integer 流程定义版本号
	 */
	Integer findLatestVersion(String name);
}
