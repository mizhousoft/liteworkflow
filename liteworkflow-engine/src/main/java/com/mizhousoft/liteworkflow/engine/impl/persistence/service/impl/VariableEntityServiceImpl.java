package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.HistoricVariable;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.Variable;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.VariableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.HistoricVariableUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.VariableUtils;

/**
 * 变量实体服务
 *
 * @version
 */
public class VariableEntityServiceImpl implements VariableEntityService
{
	/**
	 * 变量持久层
	 */
	private VariableMapper variableMapper;

	/**
	 * 历史变量实体服务
	 */
	private HistoricVariableEntityService historicVariableEntityService;

	/**
	 * 构造函数
	 *
	 * @param variableMapper
	 * @param historicVariableEntityService
	 */
	public VariableEntityServiceImpl(VariableMapper variableMapper, HistoricVariableEntityService historicVariableEntityService)
	{
		super();
		this.variableMapper = variableMapper;
		this.historicVariableEntityService = historicVariableEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Variable> addVariables(int instanceId, int taskId, Map<String, Object> variableMap)
	{
		List<Variable> variables = new ArrayList<>(variableMap.size());

		Iterator<Entry<String, Object>> iter = variableMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Object> entry = iter.next();
			String name = entry.getKey();
			Object value = entry.getValue();

			Pair<String, String> pair = VariableUtils.obtainDataTypeValue(value);

			Variable entity = new Variable();
			entity.setInstanceId(instanceId);
			entity.setTaskId(taskId);
			entity.setName(name);
			entity.setType(pair.getLeft());
			entity.setValue(pair.getValue());
			entity.setRevision(0);
			variableMapper.save(entity);

			HistoricVariable historicVariable = HistoricVariableUtils.build(entity);
			historicVariableEntityService.addEntity(historicVariable);

			variables.add(entity);
		}

		return variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> updateVariables(int instanceId, int taskId, Map<String, Object> variableMap)
	{
		List<Variable> variables = queryByTaskId(instanceId, taskId);

		Iterator<Entry<String, Object>> iter = variableMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Object> entry = iter.next();
			String name = entry.getKey();
			Object value = entry.getValue();

			Pair<String, String> pair = VariableUtils.obtainDataTypeValue(value);

			Variable variable = variables.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
			if (null == variable)
			{
				Variable entity = new Variable();
				entity.setInstanceId(instanceId);
				entity.setTaskId(taskId);
				entity.setName(name);
				entity.setType(pair.getLeft());
				entity.setValue(pair.getValue());
				entity.setRevision(0);
				variableMapper.save(entity);

				HistoricVariable historicVariable = HistoricVariableUtils.build(entity);
				historicVariableEntityService.addEntity(historicVariable);
			}
			else if (!variable.getType().equals(pair.getLeft()) || StringUtils.equals(variable.getValue(), pair.getValue()))
			{
				variable.setType(pair.getLeft());
				variable.setValue(pair.getValue());
				variableMapper.update(variable);

				HistoricVariable historicVariable = HistoricVariableUtils.build(variable);
				historicVariableEntityService.modifyEntity(historicVariable);
			}
		}

		Map<String, Object> returnVarMap = new HashMap<>(variableMap);

		List<Variable> notMatchList = variables.stream().filter(v -> !variableMap.containsKey(v.getName())).toList();
		for (Variable variable : notMatchList)
		{
			Object value = VariableUtils.convertValue(variable.getType(), variable.getValue());
			returnVarMap.put(variable.getName(), value);
		}

		return returnVarMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteById(int id)
	{
		int row = variableMapper.delete(id);

		historicVariableEntityService.deleteById(id);

		return row;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByInstanceId(int instanceId, boolean containTaskVar)
	{
		if (containTaskVar)
		{
			return variableMapper.deleteByInstanceId(instanceId);
		}
		else
		{
			return deleteByTaskId(instanceId, 0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByTaskId(int instanceId, int taskId)
	{
		return variableMapper.deleteByTaskId(instanceId, taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Variable> queryByTaskId(int instanceId, int taskId)
	{
		return variableMapper.findByTaskId(instanceId, taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> queryMapByTaskId(int instanceId, int taskId)
	{
		List<Variable> list = queryByTaskId(instanceId, taskId);

		return VariableUtils.convertVariables(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> queryMapByTaskIds(Set<Integer> instanceIds, Set<Integer> taskIds)
	{
		if (CollectionUtils.isEmpty(instanceIds) || CollectionUtils.isEmpty(taskIds))
		{
			return Collections.emptyMap();
		}

		List<Variable> list = variableMapper.findByInstanceIds(instanceIds);
		list = list.stream().filter(item -> taskIds.contains(item.getTaskId())).collect(Collectors.toList());

		Map<Integer, Map<String, Object>> resultMap = new HashMap<>(list.size());

		Map<Integer, List<Variable>> groupMap = list.stream().collect(Collectors.groupingBy(Variable::getTaskId));
		Iterator<Entry<Integer, List<Variable>>> iter = groupMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<Integer, List<Variable>> entry = iter.next();

			int taskId = entry.getKey();
			List<Variable> vars = entry.getValue();

			Map<String, Object> varMap = VariableUtils.convertVariables(vars);

			resultMap.put(taskId, varMap);
		}

		return resultMap;
	}
}
