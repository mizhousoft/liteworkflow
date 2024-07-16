package com.liteworkflow.engine.persistence.request;

import java.time.LocalDateTime;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

/**
 * 分页请求
 *
 * @version
 */
public class ProcessInstancePageRequest extends PageRequest
{
	private static final long serialVersionUID = -7529904373771692704L;

	/**
	 * 流程定义id
	 */
	private int processDefinitionId;

	/**
	 * 流程实例发起人
	 */
	private String initiator;

	/**
	 * 创建开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 创建结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 构造函数
	 *
	 */
	public ProcessInstancePageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.DESC, "create_Time"));
	}

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	public int getProcessDefinitionId()
	{
		return processDefinitionId;
	}

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	public void setProcessDefinitionId(int processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * 获取initiator
	 * 
	 * @return
	 */
	public String getInitiator()
	{
		return initiator;
	}

	/**
	 * 设置initiator
	 * 
	 * @param initiator
	 */
	public void setInitiator(String initiator)
	{
		this.initiator = initiator;
	}

	/**
	 * 获取startTime
	 * 
	 * @return
	 */
	public LocalDateTime getStartTime()
	{
		return startTime;
	}

	/**
	 * 设置startTime
	 * 
	 * @param startTime
	 */
	public void setStartTime(LocalDateTime startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * 获取endTime
	 * 
	 * @return
	 */
	public LocalDateTime getEndTime()
	{
		return endTime;
	}

	/**
	 * 设置endTime
	 * 
	 * @param endTime
	 */
	public void setEndTime(LocalDateTime endTime)
	{
		this.endTime = endTime;
	}
}
