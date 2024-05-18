package com.liteworkflow.engine;

/**
 * 框架抛出的所有异常都是此类（unchecked exception）
 * 
 * @author yuqs
 * @since 1.0
 */
public class SnakerException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5220859421440167454L;

	public SnakerException()
	{
		super();
	}

	public SnakerException(String msg, Throwable cause)
	{
		super(msg);
		super.initCause(cause);
	}

	public SnakerException(String msg)
	{
		super(msg);
	}

	public SnakerException(Throwable cause)
	{
		super();
		super.initCause(cause);
	}
}
