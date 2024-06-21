package com.liteworkflow.engine.persistence.request;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;

/**
 * SurrogateFindRequest
 *
 * @version
 */
public class SurrogateFindRequest extends PageRequest
{
	private static final long serialVersionUID = -2654740274375364249L;

	/**
	 * 名称
	 */
	private String[] names;

	/**
	 * 操作人员id
	 */
	private String[] operators;

	private String operateTime;

	/**
	 * 构造函数
	 *
	 */
	public SurrogateFindRequest()
	{
		super();

		this.setSort(Sort.create(Direction.DESC, "sdate"));
	}

	/**
	 * 获取names
	 * 
	 * @return
	 */
	public String[] getNames()
	{
		return names;
	}

	/**
	 * 设置names
	 * 
	 * @param names
	 */
	public void setNames(String[] names)
	{
		this.names = names;
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
	 * 获取operateTime
	 * 
	 * @return
	 */
	public String getOperateTime()
	{
		return operateTime;
	}

	/**
	 * 设置operateTime
	 * 
	 * @param operateTime
	 */
	public void setOperateTime(String operateTime)
	{
		this.operateTime = operateTime;
	}

}
