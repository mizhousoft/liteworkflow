package com.mizhousoft.liteworkflow.engine.delegate;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 委托执行
 *
 * @version
 */
public interface DelegateExecution
{
	/**
	 * 获取id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 获取parentId
	 * 
	 * @return
	 */
	int getParentId();

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	int getProcessDefinitionId();

	/**
	 * 获取businessKey
	 * 
	 * @return
	 */
	String getBusinessKey();

	/**
	 * 获取priority
	 * 
	 * @return
	 */
	int getPriority();

	/**
	 * 获取startUserId
	 * 
	 * @return
	 */
	String getStartUserId();

	/**
	 * 获取createTime
	 * 
	 * @return
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	LocalDateTime getCreateTime();
}
