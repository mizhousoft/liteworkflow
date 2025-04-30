package com.mizhousoft.liteworkflow.engine.domain;

import java.time.LocalDateTime;

/**
 * 历史流程实例实体类
 * 
 * @version
 */
public interface HistoricProcessInstance
{
	/**
	 * 获取id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	void setId(int id);

	/**
	 * 获取parentId
	 * 
	 * @return
	 */
	int getParentId();

	/**
	 * 设置parentId
	 * 
	 * @param parentId
	 */
	void setParentId(int parentId);

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	int getProcessDefinitionId();

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	void setProcessDefinitionId(int processDefinitionId);

	/**
	 * 获取businessKey
	 * 
	 * @return
	 */
	String getBusinessKey();

	/**
	 * 设置businessKey
	 * 
	 * @param businessKey
	 */
	void setBusinessKey(String businessKey);

	/**
	 * 获取priority
	 * 
	 * @return
	 */
	Integer getPriority();

	/**
	 * 设置priority
	 * 
	 * @param priority
	 */
	void setPriority(Integer priority);

	/**
	 * 获取startUserId
	 * 
	 * @return
	 */
	String getStartUserId();

	/**
	 * 设置startUserId
	 * 
	 * @param startUserId
	 */
	void setStartUserId(String startUserId);

	/**
	 * 获取revision
	 * 
	 * @return
	 */
	int getRevision();

	/**
	 * 设置revision
	 * 
	 * @param revision
	 */
	void setRevision(int revision);

	/**
	 * 获取duration
	 * 
	 * @return
	 */
	long getDuration();

	/**
	 * 设置duration
	 * 
	 * @param duration
	 */
	void setDuration(long duration);

	/**
	 * 获取startTime
	 * 
	 * @return
	 */
	LocalDateTime getStartTime();

	/**
	 * 设置startTime
	 * 
	 * @param startTime
	 */
	void setStartTime(LocalDateTime startTime);

	/**
	 * 获取endTime
	 * 
	 * @return
	 */
	LocalDateTime getEndTime();

	/**
	 * 设置endTime
	 * 
	 * @param endTime
	 */
	void setEndTime(LocalDateTime endTime);

	/**
	 * 获取status
	 * 
	 * @return
	 */
	public String getStatus();

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	public void setStatus(String status);
}
