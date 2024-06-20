package com.liteworkflow;

/**
 * 框架抛出的所有异常都是此类（unchecked exception）
 * 
 * @author
 * @since 1.0
 */
public class ProcessException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5220859421440167454L;

	public ProcessException()
	{
		super();
	}

	public ProcessException(String msg, Throwable cause)
	{
		super(msg);
		super.initCause(cause);
	}

	public ProcessException(String msg)
	{
		super(msg);
	}

	public ProcessException(Throwable cause)
	{
		super();
		super.initCause(cause);
	}
}
