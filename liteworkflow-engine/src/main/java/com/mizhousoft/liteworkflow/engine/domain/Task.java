package com.mizhousoft.liteworkflow.engine.domain;

import java.time.LocalDateTime;

import com.mizhousoft.liteworkflow.engine.delegate.DelegateTask;

/**
 * 任务
 *
 * @version
 */
public interface Task extends DelegateTask
{
	/**
	 * 设置id
	 * 
	 * @param id
	 */
	void setId(int id);

	/**
	 * 设置parentTaskId
	 * 
	 * @param parentTaskId
	 */
	void setParentTaskId(int parentTaskId);

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	void setProcessDefinitionId(int processDefinitionId);

	/**
	 * 设置instanceId
	 * 
	 * @param instanceId
	 */
	void setInstanceId(int instanceId);

	/**
	 * 设置taskDefinitionKey
	 * 
	 * @param taskDefinitionKey
	 */
	void setTaskDefinitionKey(String taskDefinitionKey);

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 设置owner
	 * 
	 * @param owner
	 */
	void setOwner(String owner);

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	void setStatus(String status);

	/**
	 * 设置dueTime
	 * 
	 * @param dueTime
	 */
	void setDueTime(LocalDateTime dueTime);

	/**
	 * 设置revision
	 * 
	 * @param revision
	 */
	void setRevision(int revision);

	/**
	 * 设置createTime
	 * 
	 * @param createTime
	 */
	void setCreateTime(LocalDateTime createTime);
}
