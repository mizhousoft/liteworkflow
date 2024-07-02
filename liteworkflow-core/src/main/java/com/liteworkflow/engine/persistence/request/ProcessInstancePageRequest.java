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
	private String processDefinitionId;

	/**
	 * 操作人员id
	 */
	private String[] operators;

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

		this.setSort(Sort.create(Direction.DESC, "o.create_Time"));
	}

	/**
	 * 获取processDefinitionId
	 * 
	 * @return
	 */
	public String getProcessDefinitionId()
	{
		return processDefinitionId;
	}

	/**
	 * 设置processDefinitionId
	 * 
	 * @param processDefinitionId
	 */
	public void setProcessDefinitionId(String processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * 获取operators
	 * 
	 * @return
	 */
	public String[] getOperators()
	{
		return operators;
	}

	/**
	 * 设置operators
	 * 
	 * @param operators
	 */
	public void setOperators(String[] operators)
	{
		this.operators = operators;
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
