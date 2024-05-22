package com.liteworkflow.engine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liteworkflow.engine.Context;
import com.liteworkflow.engine.cfg.EngineConfigurationImpl;
import com.liteworkflow.engine.helper.ClassHelper;

/**
 * 简单的服务查找实现类，由 {@link EngineConfigurationImpl}设置
 * 
 * @author yuqs
 * @since 1.5
 */
public class SimpleContext implements Context
{
	/**
	 * 上下文注册的配置对象
	 */
	private Map<String, Object> contextMap = new HashMap<String, Object>();

	/**
	 * 根据服务名称查找对象
	 * 
	 * @param name
	 * @return
	 */
	public boolean exist(String name)
	{
		return contextMap.get(name) != null;
	}

	/**
	 * 对外部提供的查找对象方法，根据class类型查找
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T find(Class<T> clazz)
	{
		for (Entry<String, Object> entry : contextMap.entrySet())
		{
			if (clazz.isInstance(entry.getValue()))
			{
				return clazz.cast(entry.getValue());
			}
		}
		return null;
	}

	/**
	 * 对外部提供的查找对象实例列表方法，根据class类型查找
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findList(Class<T> clazz)
	{
		List<T> list = new ArrayList<T>();
		for (Entry<String, Object> entry : contextMap.entrySet())
		{
			if (clazz.isInstance(entry.getValue()))
			{
				list.add(clazz.cast(entry.getValue()));
			}
		}
		return list;
	}

	/**
	 * 对外部提供的查找对象方法，根据名称、class类型查找
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T> T findByName(String name, Class<T> clazz)
	{
		for (Entry<String, Object> entry : contextMap.entrySet())
		{
			if (entry.getKey().equals(name) && clazz.isInstance(entry.getValue()))
			{
				return clazz.cast(entry.getValue());
			}
		}
		return null;
	}

	/**
	 * 对外部提供的put方法
	 * 
	 * @param name
	 * @param object
	 */
	public void put(String name, Object object)
	{
		contextMap.put(name, object);
	}

	/**
	 * 对外部提供的put方法
	 * 
	 * @param name
	 * @param clazz
	 */
	public void put(String name, Class<?> clazz)
	{
		contextMap.put(name, ClassHelper.instantiate(clazz));
	}
}
