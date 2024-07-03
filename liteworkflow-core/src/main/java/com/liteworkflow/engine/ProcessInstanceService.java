package com.liteworkflow.engine;

import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程实例业务类
 * 
 * @version
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
	 * 根据filter分页查询流程实例列表
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<ProcessInstance> 活动实例集合
	 */
	Page<ProcessInstance> queryPageData(ProcessInstancePageRequest request);

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
