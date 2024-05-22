package com.liteworkflow;

/**
 * 框架抛出的所有异常都是此类（unchecked exception）
 * 
 * @author yuqs
 * @since 1.0
 */
public class WorkflowException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5220859421440167454L;

	public WorkflowException()
	{
		super();
	}

	public WorkflowException(String msg, Throwable cause)
	{
		super(msg);
		super.initCause(cause);
	}

	public WorkflowException(String msg)
	{
		super(msg);
	}

	public WorkflowException(Throwable cause)
	{
		super();
		super.initCause(cause);
	}
}
