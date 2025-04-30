package com.mizhousoft.liteworkflow.engine.domain;

import java.time.LocalDateTime;

import com.mizhousoft.liteworkflow.engine.delegate.DelegateExecution;

/**
 * 流程实例
 * 
 * @version
 */
public interface ProcessInstance extends DelegateExecution
{
	/**
	 * 设置id
	 * 
	 * @param id
	 */
	void setId(int id);

	/**
	 * 设置parentId
	 * 
	 * @param parentId
	 */
	void setParentId(int parentId);

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	void setProcessDefinitionId(int processDefinitionId);

	/**
	 * 设置businessKey
	 * 
	 * @param businessKey
	 */
	void setBusinessKey(String businessKey);

	/**
	 * 设置priority
	 * 
	 * @param priority
	 */
	void setPriority(int priority);

	/**
	 * 设置revision
	 * 
	 * @param revision
	 */
	void setRevision(int revision);

	/**
	 * 设置startUserId
	 * 
	 * @param startUserId
	 */
	void setStartUserId(String startUserId);

	/**
	 * 设置createTime
	 * 
	 * @param createTime
	 */
	void setCreateTime(LocalDateTime createTime);
}
