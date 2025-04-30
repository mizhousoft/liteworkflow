package com.mizhousoft.liteworkflow.engine.exception;

import com.mizhousoft.liteworkflow.engine.WorkFlowException;

/**
 * 异常
 *
 */
public class AssigneeNullException extends WorkFlowException
{

	private static final long serialVersionUID = -8405308654634520459L;

	/**
	 * 构造函数
	 *
	 * @param message
	 * @param cause
	 */
	public AssigneeNullException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * 构造函数
	 *
	 * @param message
	 */
	public AssigneeNullException(String message)
	{
		super(message);
	}

	/**
	 * 构造函数
	 *
	 * @param cause
	 */
	public AssigneeNullException(Throwable cause)
	{
		super(cause);
	}

}
