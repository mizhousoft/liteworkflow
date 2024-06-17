package com.liteworkflow.engine;

import java.io.InputStream;
import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.ProcessDefPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @author yuqs
 * @since 1.0
 */
public interface RepositoryService
{
	/**
	 * 检查流程定义对象
	 * 
	 * @param process 流程定义对象
	 * @param idOrName 流程定义id/name
	 */
	void check(ProcessDefinition process, String idOrName);

	/**
	 * 保存流程定义
	 * 
	 * @param process 流程定义对象
	 */
	void saveProcess(ProcessDefinition process);

	/**
	 * 更新流程定义的类别
	 * 
	 * @param id 流程定义id
	 * @param type 类别
	 * @since 1.5
	 */
	void updateType(String id, String type);

	/**
	 * 根据主键ID获取流程定义对象
	 * 
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	ProcessDefinition getProcessById(String id);

	/**
	 * 根据流程name获取流程定义对象
	 * 
	 * @param name 流程定义名称
	 * @return Process 流程定义对象
	 */
	ProcessDefinition getProcessByName(String name);

	/**
	 * 根据流程name、version获取流程定义对象
	 * 
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @return Process 流程定义对象
	 */
	ProcessDefinition getProcessByVersion(String name, Integer version);

	/**
	 * 根据给定的参数列表args查询process
	 * 
	 * @param filter 查询过滤器
	 * @return List<Process> 流程定义对象集合
	 */
	List<ProcessDefinition> getProcesss(ProcessDefPageRequest request);

	/**
	 * 根据给定的参数列表args分页查询process
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<Process> 流程定义对象集合
	 */
	Page<ProcessDefinition> queryPageData(ProcessDefPageRequest request);

	/**
	 * 根據InputStream輸入流，部署流程定义
	 * 
	 * @param input 流程定义输入流
	 * @return String 流程定义id
	 */
	String deploy(InputStream input);

	/**
	 * 根據InputStream輸入流，部署流程定义
	 * 
	 * @param input 流程定义输入流
	 * @param creator 创建人
	 * @return String 流程定义id
	 */
	String deploy(InputStream input, String creator);

	/**
	 * 根據InputStream輸入流，部署流程定义
	 * 
	 * @param id 流程定义id
	 * @param input 流程定义输入流
	 */
	void redeploy(String id, InputStream input);

	/**
	 * 卸载指定的流程定义，只更新状态
	 * 
	 * @param id 流程定义id
	 */
	void undeploy(String id);

	/**
	 * 谨慎使用.数据恢复非常痛苦，你懂得~~
	 * 级联删除指定流程定义的所有数据：
	 * 1.wf_process
	 * 2.wf_process_instance,wf_hist_process_instance
	 * 3.wf_task,wf_hist_task
	 * 4.wf_task_actor,wf_hist_task_actor
	 * 5.wf_cc_order
	 * 
	 * @param id
	 */
	void cascadeRemove(String id);
}
