package com.mizhousoft.liteworkflow.engine.impl.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricVariable;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;

/**
 * 变量工具类
 *
 * @version
 */
public abstract class HistoricVariableUtils
{
	/**
	 * 转换变量
	 * 
	 * @param variables
	 * @return
	 */
	public static Map<String, Object> convertVariables(List<HistoricVariable> variables)
	{
		Map<String, Object> variableMap = new HashMap<>(variables.size());

		for (HistoricVariable variable : variables)
		{
			Object value = VariableUtils.convertValue(variable.getType(), variable.getValue());
			variableMap.put(variable.getName(), value);
		}

		return variableMap;
	}

	public static HistoricVariable build(Variable variable)
	{
		HistoricVariable historicVariable = new HistoricVariable();
		historicVariable.setInstanceId(variable.getInstanceId());
		historicVariable.setTaskId(variable.getTaskId());
		historicVariable.setName(variable.getName());
		historicVariable.setType(variable.getType());
		historicVariable.setValue(variable.getValue());
		historicVariable.setRevision(variable.getRevision());
		historicVariable.setCreateTime(LocalDateTime.now());
		historicVariable.setLastUpdateTime(LocalDateTime.now());

		return historicVariable;
	}
}
