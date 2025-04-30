package com.mizhousoft.liteworkflow.engine.impl.persistence.service;

import java.util.List;

import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;

/**
 * 部署管理器
 *
 * @version
 */
public interface DeploymentManager
{
	/**
	 * 加入缓存
	 * 
	 * @param processDefinition
	 */
	void putCache(ProcessDefEntity processDefinition);

	/**
	 * 移除缓存
	 * 
	 * @param processDefinition
	 */
	void removeCache(ProcessDefEntity processDefinition);

	/**
	 * 根据ID获取流程定义对象
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	ProcessDefEntity getProcessDefinition(int processDefinitionId);

	/**
	 * 根据流程定义Key获取流程定义对象
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessDefEntity getLatestByKey(String processDefinitionKey);

	/**
	 * 根据流程定义processDefinitionKey、version获取流程定义对象
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	ProcessDefEntity getByVersion(String processDefinitionKey, Integer version);

	/**
	 * 根据流程定义Key查询
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	List<ProcessDefEntity> queryList(String processDefinitionKey);
}
