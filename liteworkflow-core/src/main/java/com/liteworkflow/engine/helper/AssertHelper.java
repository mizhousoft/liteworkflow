package com.liteworkflow.engine.helper;

/**
 * 断言帮助类
 * 
 * @author
 * @since 1.0
 */
public abstract class AssertHelper
{
	/**
	 * 断言给定的object对象为非空
	 * 
	 * @param object
	 * @param message 异常打印信息
	 */
	public static void notNull(Object object, String message)
	{
		if (object == null)
		{
			throw new IllegalArgumentException(message);
		}
	}

}
