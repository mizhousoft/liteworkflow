package org.snaker.engine.cache;

import org.snaker.engine.SnakerException;

/**
 * cache异常
 * 
 * @author yuqs
 * @since 1.3
 */
public class CacheException extends SnakerException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5329674600226403430L;

	/**
	 * 创建cache异常
	 */
	public CacheException()
	{
		super();
	}

	/**
	 * 创建cache异常
	 * 
	 * @param message
	 */
	public CacheException(String message)
	{
		super(message);
	}

	/**
	 * 创建cache异常
	 * 
	 * @param cause
	 */
	public CacheException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * 创建cache异常
	 * 
	 * @param message
	 * @param cause
	 */
	public CacheException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
