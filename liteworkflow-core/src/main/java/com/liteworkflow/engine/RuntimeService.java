package com.liteworkflow.engine;

import java.util.Map;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * TODO
 *
 * @version
 */
public interface RuntimeService
{

	/**
	 * 根据流程定义ID启动流程实例
	 * 
	 * @param id 流程定义ID
	 * @return ProcessInstance 流程实例
	 * @see #startInstanceById(String, String, Map)
	 */
	ProcessInstance startInstanceById(String id);

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 * 
	 * @param id 流程定义ID
	 * @param operator 操作人ID
	 * @return ProcessInstance 流程实例
	 * @see #startInstanceById(String, String, Map)
	 */
	ProcessInstance startInstanceById(String id, String operator);

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 * 
	 * @param id 流程定义ID
	 * @param operator 操作人ID
	 * @param args 参数列表
	 * @return ProcessInstance 流程实例
	 */
	ProcessInstance startInstanceById(String id, String operator, Map<String, Object> args);

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @return ProcessInstance 流程实例
	 */
	ProcessInstance startInstanceByName(String name);

	/**
	 * 根据流程名称、版本号、操作人启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @param operator 操作人
	 * @return ProcessInstance 流程实例
	 */
	ProcessInstance startInstanceByName(String name, String operator);

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @param operator 操作人
	 * @param args 参数列表
	 * @return ProcessInstance 流程实例
	 */
	ProcessInstance startInstanceByName(String name, String operator, Map<String, Object> args);

	void setVariable(String instanceId, String variableName, Object value);

	void setVariables(String instanceId, Map<String, Object> variableMap);
}
