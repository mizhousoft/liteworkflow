package com.mizhousoft.liteworkflow.engine.request;

import java.time.LocalDateTime;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

/**
 * 分页请求
 *
 * @version
 */
public class HistoricInstancePageRequest extends PageRequest
{
	private static final long serialVersionUID = -2630784391027545999L;

	/**
	 * 流程定义id
	 */
	private int processDefinitionId;

	/**
	 * 流程实例发起人
	 */
	private String startUserId;

	/**
	 * 创建开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 创建结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 构造函数
	 *
	 */
	public HistoricInstancePageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.DESC, "id"));
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
	 * 获取startUserId
	 * 
	 * @return
	 */
	public String getStartUserId()
	{
		return startUserId;
	}

	/**
	 * 设置startUserId
	 * 
	 * @param startUserId
	 */
	public void setStartUserId(String startUserId)
	{
		this.startUserId = startUserId;
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

	/**
	 * 获取status
	 * 
	 * @return
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}
}
