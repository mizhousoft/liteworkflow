package com.liteworkflow.engine.helper;

/**
 * 字符串处理帮助类
 * 
 * @author
 * @since 1.0
 */
public class StringHelper
{
	/**
	 * 获取uuid类型的字符串
	 * 
	 * @return uuid字符串
	 */
	public static String getPrimaryKey()
	{
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
}
