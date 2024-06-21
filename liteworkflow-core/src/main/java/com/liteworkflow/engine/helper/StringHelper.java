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

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str 字符串
	 * @return 是否为空标识
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.length() == 0;
	}

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str 字符串
	 * @return 是否为非空标识
	 */
	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}

	/**
	 * 根据字符串数组返回逗号分隔的字符串值
	 * 
	 * @param strArray 字符串数组
	 * @return 逗号分隔的字符串
	 */
	public static String getStringByArray(String... strArray)
	{
		if (strArray == null)
			return "";
		StringBuilder buffer = new StringBuilder(strArray.length * 10);
		for (String str : strArray)
		{
			buffer.append(str).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}
}
