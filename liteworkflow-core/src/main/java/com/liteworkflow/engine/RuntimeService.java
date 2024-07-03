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
	 * @param args
	 * @return
	 */
	ProcessInstance startInstanceById(String processDefinitionId, String operator, Map<String, Object> args);

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @param processDefinitionName
	 * @return
	 */
	ProcessInstance startInstanceByName(String processDefinitionName);

	/**
	 * 根据流程名称、版本号、操作人启动流程实例
	 * 
	 * @param processDefinitionName
	 * @param operator
	 * @return
	 */
	ProcessInstance startInstanceByName(String processDefinitionName, String operator);

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @param processDefinitionName
	 * @param operator
	 * @param args
	 * @return
	 */
	ProcessInstance startInstanceByName(String processDefinitionName, String operator, Map<String, Object> args);

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
