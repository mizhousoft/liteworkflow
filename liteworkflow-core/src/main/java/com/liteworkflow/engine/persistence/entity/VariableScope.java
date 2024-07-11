package com.liteworkflow.engine.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import com.liteworkflow.engine.helper.JsonHelper;

/**
 * 变量作用域
 *
 * @version
 */
public abstract class VariableScope
{
	/**
	 * 变量
	 */
	private Map<String, Object> variableMap;

	/**
	 * 获取variableMap
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getVariableMap()
	{
		if (null == variableMap)
		{
			Map<String, Object> dataMap = JsonHelper.fromJson(getVariable(), Map.class);
			if (null == dataMap)
			{
				dataMap = new HashMap<>(0);
			}

			variableMap = dataMap;
		}

		return variableMap;
	}

	/**
	 * 获取变量
	 * 
	 * @return
	 */
	public abstract String getVariable();
}
