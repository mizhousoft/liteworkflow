package com.liteworkflow.engine;

import java.util.Map;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.WorkItem;
import com.liteworkflow.engine.persistence.request.WorkItemPageRequest;
import com.mizhousoft.commons.data.domain.Page;

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
	public ProcessInstance startInstanceById(String id);

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 * 
	 * @param id 流程定义ID
	 * @param operator 操作人ID
	 * @return ProcessInstance 流程实例
	 * @see #startInstanceById(String, String, Map)
	 */
	public ProcessInstance startInstanceById(String id, String operator);

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 * 
	 * @param id 流程定义ID
	 * @param operator 操作人ID
	 * @param args 参数列表
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceById(String id, String operator, Map<String, Object> args);

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceByName(String name);

	/**
	 * 根据流程名称、版本号启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceByName(String name, Integer version);

	/**
	 * 根据流程名称、版本号、操作人启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @param operator 操作人
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceByName(String name, Integer version, String operator);

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @param operator 操作人
	 * @param args 参数列表
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceByName(String name, Integer version, String operator, Map<String, Object> args);

	/**
	 * 根据父执行对象启动子流程实例
	 * 
	 * @param execution 执行对象
	 * @return ProcessInstance 流程实例
	 */
	public ProcessInstance startInstanceByExecution(Execution execution);

	/**
	 * 根据filter分页查询工作项（包含process、ProcessInstance、task三个实体的字段集合）
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 活动工作项集合
	 */
	Page<WorkItem> getWorkItems(WorkItemPageRequest request);

}
