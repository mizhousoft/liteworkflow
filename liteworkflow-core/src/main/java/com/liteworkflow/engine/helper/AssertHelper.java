package com.liteworkflow.engine.helper;

/**
 * 断言帮助类
 * 
 * @author yuqs
 * @since 1.0
 */
public abstract class AssertHelper
{
	/**
	 * 断言表达式为true
	 * 
	 * @param expression
	 * @param message 异常打印信息
	 */
	public static void isTrue(boolean expression, String message)
	{
		if (!expression)
		{
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言表达式为true
	 * 
	 * @param expression
	 */
	public static void isTrue(boolean expression)
	{
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 断言给定的object对象为空
	 * 
	 * @param object
	 * @param message 异常打印信息
	 */
	public static void isNull(Object object, String message)
	{
		if (object != null)
		{
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定的object对象为空
	 * 
	 * @param object
	 */
	public static void isNull(Object object)
	{
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

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

	/**
	 * 断言给定的object对象为非空
	 * 
	 * @param object
	 */
	public static void notNull(Object object)
	{
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * 断言给定的字符串为非空
	 * 
	 * @param str
	 */
	public static void notEmpty(String str)
	{
		notEmpty(str, "[Assertion failed] - this argument is required; it must not be null or empty");
	}

	/**
	 * 断言给定的字符串为非空
	 * 
	 * @param str
	 * @param message
	 */
	public static void notEmpty(String str, String message)
	{
		if (str == null || str.length() == 0)
		{
			throw new IllegalArgumentException(message);
		}
	}
}
