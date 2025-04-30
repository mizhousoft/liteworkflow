package com.mizhousoft.liteworkflow.engine.impl.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.mizhousoft.commons.lang.LocalDateTimeUtils;
import com.mizhousoft.commons.lang.LocalDateUtils;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;

/**
 * 变量工具类
 *
 * @version
 */
public abstract class VariableUtils
{
	/**
	 * 转换变量
	 * 
	 * @param variables
	 * @return
	 */
	public static Map<String, Object> convertVariables(List<Variable> variables)
	{
		Map<String, Object> variableMap = new HashMap<>(variables.size());

		for (Variable variable : variables)
		{
			Object value = VariableUtils.convertValue(variable.getType(), variable.getValue());
			variableMap.put(variable.getName(), value);
		}

		return variableMap;
	}

	/**
	 * 转换值
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object convertValue(String type, String value)
	{
		if (type.equals("null"))
		{
			return null;
		}
		else if (type.equals("integer"))
		{
			return Integer.valueOf(value);
		}
		else if (type.equals("long"))
		{
			return Long.valueOf(value);
		}
		else if (type.equals("string"))
		{
			return value;
		}
		else if (type.equals("boolean"))
		{
			return Boolean.valueOf(value);
		}
		else if (type.equals("localDateTime"))
		{
			long digitValue = Long.valueOf(value);

			return LocalDateTimeUtils.toLocalDateTime(digitValue / 1000);
		}
		else if (type.equals("localDate"))
		{
			long digitValue = Long.valueOf(value);

			return LocalDateUtils.toLocalDate(digitValue);
		}
		else
		{
			return null;
		}
	}

	/**
	 * 获取数据类型
	 * 
	 * @param value
	 * @return
	 */
	public static Pair<String, String> obtainDataTypeValue(Object value)
	{
		if (null == value)
		{
			return ImmutablePair.of("null", null);
		}
		else if (value instanceof Integer)
		{
			return ImmutablePair.of("integer", value.toString());
		}
		else if (value instanceof Long)
		{
			return ImmutablePair.of("long", value.toString());
		}
		else if (value instanceof String)
		{
			return ImmutablePair.of("string", value.toString());
		}
		else if (value instanceof Boolean)
		{
			return ImmutablePair.of("boolean", value.toString());
		}
		else if (value instanceof LocalDateTime dt)
		{
			return ImmutablePair.of("localDateTime", String.valueOf(LocalDateTimeUtils.toTimestamp(dt)));
		}
		else if (value instanceof LocalDate d)
		{
			return ImmutablePair.of("localDate", String.valueOf(LocalDateUtils.toTimestamp(d)));
		}
		else
		{
			throw new WorkFlowException("Data type not support, value is " + value.getClass().getName());
		}
	}
}
