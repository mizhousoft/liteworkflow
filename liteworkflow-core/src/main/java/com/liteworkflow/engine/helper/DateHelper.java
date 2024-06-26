package com.liteworkflow.engine.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.lang.LocalDateTimeUtils;
import com.mizhousoft.commons.lang.LocalDateUtils;

/**
 * 日期帮助类
 * 
 * @author
 * @since 1.0
 */
public class DateHelper
{
	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 返回标准格式的当前时间
	 * 
	 * @return
	 */
	public static String getTime()
	{
		return LocalDateTimeUtils.format(LocalDateTime.now(), DATE_FORMAT_DEFAULT);
	}

	/**
	 * 解析日期时间对象
	 * 
	 * @param date
	 * @return
	 */
	public static String parseTime(Object date)
	{
		if (date == null)
			return null;
		if (date instanceof Date)
		{
			LocalDateTime ls = LocalDateTimeUtils.toLocalDateTime((Date) date);
			return LocalDateTimeUtils.format(ls, DATE_FORMAT_DEFAULT);
		}
		else if (date instanceof String)
		{
			return String.valueOf(date);
		}
		return "";
	}

	/**
	 * 对时限数据进行处理
	 * 1、运行时设置的date型数据直接返回
	 * 2、模型设置的需要特殊转换成date类型
	 * 3、运行时设置的转换为date型
	 * 
	 * @param args 运行时参数
	 * @param parameter 模型参数
	 * @return Date类型
	 */
	public static LocalDate processTime(Map<String, Object> args, String parameter)
	{
		if (StringUtils.isBlank(parameter))
			return null;
		Object data = args.get(parameter);
		if (data == null)
			data = parameter;

		LocalDate result = null;
		if (data instanceof LocalDate)
		{
			return (LocalDate) data;
		}
		else if (data instanceof Long)
		{
			return LocalDateUtils.toLocalDate(new Date((Long) data));
		}
		else if (data instanceof String)
		{
			// TODO 1.4-dev ignore
		}
		return result;
	}
}
