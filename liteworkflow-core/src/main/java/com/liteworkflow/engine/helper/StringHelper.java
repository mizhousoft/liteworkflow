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
