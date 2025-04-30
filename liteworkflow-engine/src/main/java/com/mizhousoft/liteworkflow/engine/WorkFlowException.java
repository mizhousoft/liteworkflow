package com.mizhousoft.liteworkflow.engine;

/**
 * 工作流异常
 * 
 * @version
 */
public class WorkFlowException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5220859421440167454L;

	/**
	 * 构造函数
	 *
	 * @param message
	 * @param cause
	 */
	public WorkFlowException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * 构造函数
	 *
	 * @param message
	 */
	public WorkFlowException(String message)
	{
		super(message);
	}

	/**
	 * 构造函数
	 *
	 * @param cause
	 */
	public WorkFlowException(Throwable cause)
	{
		super(cause);
	}
}
