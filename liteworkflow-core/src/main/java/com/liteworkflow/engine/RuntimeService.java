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
	 * 根据流程定义ID，发起人启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param initiator
	 * @return
	 */
	ProcessInstance startInstanceById(int processDefinitionId, String initiator);

	/**
	 * 根据流程定义ID，业务KEY，发起人启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @param initiator
	 * @return
	 */
	ProcessInstance startInstanceById(int processDefinitionId, String businessKey, String initiator);

	/**
	 * 根据流程定义ID，业务KEY，发起人，参数列表启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @param initiator
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceById(int processDefinitionId, String businessKey, String initiator, Map<String, Object> variableMap);

	/**
	 * 根据流程Key、发起人启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param initiator
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String initiator);

	/**
	 * 根据流程Key、业务KEY，发起人启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param initiator
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, String initiator);

	/**
	 * 根据流程Key、业务KEY，发起人、参数列表启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param initiator
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, String initiator, Map<String, Object> variableMap);

	/**
	 * 设置流程发起人
	 * 
	 * @param instanceId
	 * @param owner
	 */
	void setOwner(int instanceId, String owner);

	/**
	 * 设置流程变量
	 * 
	 * @param instanceId
	 * @param variableName
	 * @param value
	 */
	void setVariable(int instanceId, String variableName, Object value);

	/**
	 * 设置流程变量
	 * 
	 * @param instanceId
	 * @param variableMap
	 */
	void setVariables(int instanceId, Map<String, Object> variableMap);

	/**
	 * 删除流程实例
	 * 
	 * @param instanceId
	 */
	void deleteInstance(int instanceId);
}
