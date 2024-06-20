package com.liteworkflow.engine;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程实例业务类
 * 
 * @author
 * @since 1.0
 */
public interface ProcessInstanceService
{
	/**
	 * 根据流程实例ID获取流程实例对象
	 * 
	 * @param instanceId 流程实例id
	 * @return ProcessInstance 流程实例对象
	 */
	ProcessInstance getInstance(String instanceId);

	/**
	 * 根据filter查询流程实例列表
	 * 
	 * @param filter 查询过滤器
	 * @return List<ProcessInstance> 活动实例集合
	 */
	List<ProcessInstance> getActiveInstances(ProcessInstPageRequest request);

	/**
	 * 根据filter分页查询流程实例列表
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<ProcessInstance> 活动实例集合
	 */
	Page<ProcessInstance> queryPageData(ProcessInstPageRequest request);

	/**
	 * 根据流程、操作人员、父流程实例ID创建流程实例
	 * 
	 * @param process 流程定义对象
	 * @param operator 操作人员ID
	 * @param args 参数列表
	 * @return ProcessInstance 活动流程实例对象
	 */
	ProcessInstance createInstance(ProcessDefinition process, String operator, Map<String, Object> args);

	/**
	 * 根据流程、操作人员、父流程实例ID创建流程实例
	 * 
	 * @param process 流程定义对象
	 * @param operator 操作人员ID
	 * @param args 参数列表
	 * @param parentId 父流程实例ID
	 * @param parentNodeName 父流程节点模型
	 * @return 活动流程实例对象
	 */
	ProcessInstance createInstance(ProcessDefinition process, String operator, Map<String, Object> args, String parentId,
	        String parentNodeName);

	/**
	 * 向指定实例id添加全局变量数据
	 * 
	 * @param instanceId 实例id
	 * @param args 变量数据
	 */
	void addVariable(String instanceId, Map<String, Object> args);

	/**
	 * 创建抄送实例
	 * 
	 * @param instanceId 流程实例id
	 * @param actorIds 参与者id
	 * @param creator 创建人id
	 * @since 1.5
	 */
	void createCCInstance(String instanceId, String creator, String... actorIds);

	/**
	 * 流程实例正常完成
	 * 
	 * @param instanceId 流程实例id
	 */
	void complete(String instanceId);

	/**
	 * 流程实例强制终止
	 * 
	 * @param instanceId 流程实例id
	 */
	void terminate(String instanceId);

	/**
	 * 流程实例强制终止
	 * 
	 * @param instanceId 流程实例id
	 * @param operator 处理人员
	 */
	void terminate(String instanceId, String operator);

	/**
	 * 唤醒历史流程实例
	 * 
	 * @param instanceId 流程实例id
	 * @return 活动实例对象
	 */
	ProcessInstance resume(String instanceId);

	/**
	 * 更新流程实例
	 * 
	 * @param instance 流程实例对象
	 */
	void updateInstance(ProcessInstance instance);

	/**
	 * 更新抄送记录为已阅
	 * 
	 * @param instanceId 流程实例id
	 * @param actorIds 参与者id
	 */
	void updateCCStatus(String instanceId, String... actorIds);

	/**
	 * 删除抄送记录
	 * 
	 * @param instanceId 流程实例id
	 * @param actorId 参与者id
	 */
	void deleteCCInstance(String instanceId, String actorId);

	/**
	 * 谨慎使用.数据恢复非常痛苦，你懂得~~
	 * 级联删除指定流程实例的所有数据：
	 * 1.wf_process_instance,wf_historic_process_instance
	 * 2.wf_task,wf_historic_task
	 * 3.wf_task_actor,wf_historic_task_actor
	 * 4.wf_cc_process_instance
	 * 
	 * @param id
	 */
	void cascadeRemove(String id);
}
