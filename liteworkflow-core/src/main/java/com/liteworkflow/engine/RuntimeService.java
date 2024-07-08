package com.liteworkflow.engine;

import java.util.Map;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * 流程运行时服务
 *
 * @version
 */
public interface RuntimeService
{
	/**
	 * 根据流程定义ID启动流程实例
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	ProcessInstance startInstanceById(String processDefinitionId);

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param operator
	 * @return
	 */
	ProcessInstance startInstanceById(String processDefinitionId, String operator);

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param operator
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceById(String processDefinitionId, String operator, Map<String, Object> variableMap);

	/**
	 * 根据流程Key启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey);

	/**
	 * 根据流程Key、操作人启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param operator
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String operator);

	/**
	 * 根据流程Key、操作人、参数列表启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param operator
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String operator, Map<String, Object> variableMap);

	/**
	 * 设置流程发起人
	 * 
	 * @param instanceId
	 * @param owner
	 */
	void setOwner(String instanceId, String owner);

	/**
	 * 设置流程变量
	 * 
	 * @param instanceId
	 * @param variableName
	 * @param value
	 */
	void setVariable(String instanceId, String variableName, Object value);

	/**
	 * 设置流程变量
	 * 
	 * @param instanceId
	 * @param variableMap
	 */
	void setVariables(String instanceId, Map<String, Object> variableMap);
}
