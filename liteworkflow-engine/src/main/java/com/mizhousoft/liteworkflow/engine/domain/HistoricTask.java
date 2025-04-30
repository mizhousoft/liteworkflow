package com.mizhousoft.liteworkflow.engine.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 历史任务
 * 
 * @version
 */
public interface HistoricTask
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
	 * 获取parentTaskId
	 * 
	 * @return
	 */
	int getParentTaskId();

	/**
	 * 设置parentTaskId
	 * 
	 * @param parentTaskId
	 */
	void setParentTaskId(int parentTaskId);

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
	 * 获取instanceId
	 * 
	 * @return
	 */
	int getInstanceId();

	/**
	 * 设置instanceId
	 * 
	 * @param instanceId
	 */
	void setInstanceId(int instanceId);

	/**
	 * 获取taskDefinitionKey
	 * 
	 * @return
	 */
	String getTaskDefinitionKey();

	/**
	 * 设置taskDefinitionKey
	 * 
	 * @param taskDefinitionKey
	 */
	void setTaskDefinitionKey(String taskDefinitionKey);

	/**
	 * 获取name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 获取assignee
	 * 
	 * @return
	 */
	String getAssignee();

	/**
	 * 设置assignee
	 * 
	 * @param assignee
	 */
	void setAssignee(String assignee);

	/**
	 * 获取status
	 * 
	 * @return
	 */
	String getStatus();

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	void setStatus(String status);

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	LocalDateTime getEndTime();

	/**
	 * 设置endTime
	 * 
	 * @param endTime
	 */
	void setEndTime(LocalDateTime endTime);
}
