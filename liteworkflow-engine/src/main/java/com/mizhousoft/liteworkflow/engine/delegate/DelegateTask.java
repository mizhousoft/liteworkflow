package com.mizhousoft.liteworkflow.engine.delegate;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 委托任务
 *
 * @version
 */
public interface DelegateTask
{
	/**
	 * 获取ID
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 获取父任务ID
	 * 
	 * @return
	 */
	int getParentTaskId();

	/**
	 * 获取流程定义ID
	 * 
	 * @return
	 */
	int getProcessDefinitionId();

	/**
	 * 获取流程实例ID
	 * 
	 * @return
	 */
	int getInstanceId();

	/**
	 * 获取任务定义ID
	 * 
	 * @return
	 */
	String getTaskDefinitionKey();

	/**
	 * 获取任务名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取owner
	 * 
	 * @return
	 */
	String getOwner();

	/**
	 * 获取执行人
	 * 
	 * @return
	 */
	String getAssignee();

	/**
	 * 设置执行人
	 * 
	 * @param assignee
	 */
	void setAssignee(String assignee);

	/**
	 * 获取状态
	 * 
	 * @return
	 */
	String getStatus();

	/**
	 * 获取到期时间
	 * 
	 * @return
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	LocalDateTime getDueTime();

	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	LocalDateTime getCreateTime();
}
