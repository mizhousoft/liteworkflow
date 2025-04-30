package com.mizhousoft.liteworkflow.engine.cache;

import com.mizhousoft.liteworkflow.engine.WorkFlowException;

/**
 * 缓存异常
 * 
 * @version
 */
public class CacheException extends WorkFlowException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5329674600226403430L;

	/**
	 * 构造函数
	 *
	 * @param message
	 * @param cause
	 */
	public CacheException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * 构造函数
	 *
	 * @param message
	 */
	public CacheException(String message)
	{
		super(message);
	}

	/**
	 * 构造函数
	 *
	 * @param cause
	 */
	public CacheException(Throwable cause)
	{
		super(cause);
	}
}
