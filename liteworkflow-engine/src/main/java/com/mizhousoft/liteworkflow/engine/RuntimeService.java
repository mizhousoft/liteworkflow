package com.mizhousoft.liteworkflow.engine;

import java.util.Collection;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;

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
	ProcessInstance startInstanceById(int processDefinitionId);

	/**
	 * 根据流程定义ID，业务KEY启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @return
	 */
	ProcessInstance startInstanceById(int processDefinitionId, String businessKey);

	/**
	 * 根据流程定义ID，业务KEY，参数列表启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceById(int processDefinitionId, String businessKey, Map<String, Object> variableMap);

	/**
	 * 根据流程Key启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey);

	/**
	 * 根据流程Key、业务KEY启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey);

	/**
	 * 根据流程Key、业务KEY、参数列表启动流程实例
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variableMap
	 * @return
	 */
	ProcessInstance startInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variableMap);

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
	 * 移除流程变量
	 * 
	 * @param instanceId
	 * @param variableName
	 */
	Object removeVariable(int instanceId, String variableName);

	/**
	 * 移除流程变量
	 * 
	 * @param instanceId
	 * @param variableNames
	 */
	Map<String, Object> removeVariables(int instanceId, Collection<String> variableNames);

	/**
	 * 获取流程变量
	 * 
	 * @param instanceId
	 * @return
	 */
	Map<String, Object> getVariables(int instanceId);

	/**
	 * 删除流程实例
	 * 
	 * @param instanceId
	 */
	void deleteInstance(int instanceId);

	/**
	 * 撤销流程实例
	 * 
	 * @param instanceId
	 */
	void terminateInstance(int instanceId);
}
