package com.mizhousoft.liteworkflow.engine;

/**
 * 身份
 *
 * @version
 */
public abstract class Authentication
{
	private static ThreadLocal<String> authenticatedUserIdThreadLocal = new ThreadLocal<>();

	/**
	 * 获取当前线程认证用户ID
	 * 
	 * @return
	 */
	public static String getAuthenticatedUserId()
	{
		return authenticatedUserIdThreadLocal.get();
	}

	/**
	 * 设置当前线程认证用户ID
	 * 
	 * @param authenticatedUserId
	 */
	public static void setAuthenticatedUserId(String authenticatedUserId)
	{
		authenticatedUserIdThreadLocal.set(authenticatedUserId);
	}

	/**
	 * 清除数据
	 */
	public static void clear()
	{
		authenticatedUserIdThreadLocal.remove();
	}
}
