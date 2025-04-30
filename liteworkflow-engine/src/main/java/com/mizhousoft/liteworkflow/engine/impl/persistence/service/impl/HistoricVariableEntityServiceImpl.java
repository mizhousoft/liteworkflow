package com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl;

import java.time.LocalDateTime;
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
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricVariableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.HistoricVariableUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.VariableUtils;

/**
 * 变量实体服务
 *
 * @version
 */
public class HistoricVariableEntityServiceImpl implements HistoricVariableEntityService
{
	/**
	 * 变量持久层
	 */
	private HistoricVariableMapper historicVariableMapper;

	/**
	 * 构造函数
	 *
	 * @param historicVariableMapper
	 */
	public HistoricVariableEntityServiceImpl(HistoricVariableMapper historicVariableMapper)
	{
		super();
		this.historicVariableMapper = historicVariableMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addEntity(HistoricVariable entity)
	{
		return historicVariableMapper.save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int modifyEntity(HistoricVariable entity)
	{
		return historicVariableMapper.update(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateVariables(int instanceId, int taskId, Map<String, Object> variableMap)
	{
		if (variableMap.isEmpty())
		{
			return;
		}

		List<HistoricVariable> variables = historicVariableMapper.findByTaskId(instanceId, taskId);

		Iterator<Entry<String, Object>> iter = variableMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Object> entry = iter.next();
			String name = entry.getKey();
			Object value = entry.getValue();

			Pair<String, String> pair = VariableUtils.obtainDataTypeValue(value);

			HistoricVariable variable = variables.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
			if (null == variable)
			{
				HistoricVariable entity = new HistoricVariable();
				entity.setInstanceId(instanceId);
				entity.setTaskId(taskId);
				entity.setName(name);
				entity.setType(pair.getLeft());
				entity.setValue(pair.getValue());
				entity.setRevision(0);
				entity.setCreateTime(LocalDateTime.now());
				entity.setLastUpdateTime(LocalDateTime.now());

				addEntity(entity);
			}
			else if (!variable.getType().equals(pair.getLeft()) || StringUtils.equals(variable.getValue(), pair.getValue()))
			{
				variable.setType(pair.getLeft());
				variable.setValue(pair.getValue());
				modifyEntity(variable);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteById(int id)
	{
		return historicVariableMapper.delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByInstanceId(int instanceId)
	{
		return historicVariableMapper.deleteByInstanceId(instanceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByTaskId(int instanceId, int taskId)
	{
		return historicVariableMapper.deleteByTaskId(instanceId, taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> queryMapByTaskId(int instanceId, int taskId)
	{
		List<HistoricVariable> list = historicVariableMapper.findByTaskId(instanceId, taskId);

		return HistoricVariableUtils.convertVariables(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> queryMapByInstanceIds(Set<Integer> instanceIds)
	{
		if (CollectionUtils.isEmpty(instanceIds))
		{
			return Collections.emptyMap();
		}

		List<HistoricVariable> list = historicVariableMapper.findByInstanceIds(instanceIds);
		list = list.stream().filter(item -> item.getTaskId() == 0).collect(Collectors.toList());

		Map<Integer, Map<String, Object>> resultMap = new HashMap<>(list.size());

		Map<Integer, List<HistoricVariable>> groupMap = list.stream().collect(Collectors.groupingBy(HistoricVariable::getInstanceId));
		Iterator<Entry<Integer, List<HistoricVariable>>> iter = groupMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<Integer, List<HistoricVariable>> entry = iter.next();

			int instanceId = entry.getKey();
			List<HistoricVariable> vars = entry.getValue();

			Map<String, Object> varMap = HistoricVariableUtils.convertVariables(vars);

			resultMap.put(instanceId, varMap);
		}

		return resultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Map<String, Object>> queryMapByTaskIds(int instanceId, Set<Integer> taskIds)
	{
		if (CollectionUtils.isEmpty(taskIds))
		{
			return Collections.emptyMap();
		}

		List<HistoricVariable> list = historicVariableMapper.findByInstanceIds(Set.of(instanceId));
		list = list.stream().filter(item -> taskIds.contains(item.getTaskId())).collect(Collectors.toList());

		Map<Integer, Map<String, Object>> resultMap = new HashMap<>(list.size());

		Map<Integer, List<HistoricVariable>> groupMap = list.stream().collect(Collectors.groupingBy(HistoricVariable::getTaskId));
		Iterator<Entry<Integer, List<HistoricVariable>>> iter = groupMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<Integer, List<HistoricVariable>> entry = iter.next();

			int taskId = entry.getKey();
			List<HistoricVariable> vars = entry.getValue();

			Map<String, Object> varMap = HistoricVariableUtils.convertVariables(vars);

			resultMap.put(taskId, varMap);
		}

		return resultMap;
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

		List<HistoricVariable> list = historicVariableMapper.findByInstanceIds(instanceIds);
		list = list.stream().filter(item -> taskIds.contains(item.getTaskId())).collect(Collectors.toList());

		Map<Integer, Map<String, Object>> resultMap = new HashMap<>(list.size());

		Map<Integer, List<HistoricVariable>> groupMap = list.stream().collect(Collectors.groupingBy(HistoricVariable::getTaskId));
		Iterator<Entry<Integer, List<HistoricVariable>>> iter = groupMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<Integer, List<HistoricVariable>> entry = iter.next();

			int taskId = entry.getKey();
			List<HistoricVariable> vars = entry.getValue();

			Map<String, Object> varMap = HistoricVariableUtils.convertVariables(vars);

			resultMap.put(taskId, varMap);
		}

		return resultMap;
	}
}
