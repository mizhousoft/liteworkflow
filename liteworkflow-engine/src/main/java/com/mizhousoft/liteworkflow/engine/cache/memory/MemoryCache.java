package com.mizhousoft.liteworkflow.engine.cache.memory;

import java.util.Map;

import com.mizhousoft.liteworkflow.engine.cache.Cache;
import com.mizhousoft.liteworkflow.engine.cache.CacheException;

/**
 * 基于内存管理cache
 * 
 * @version
 */
public class MemoryCache<K, V> implements Cache<K, V>
{
	/**
	 * map cache
	 */
	private final Map<K, V> map;

	/**
	 * 通过Map实现类构造MemoryCache
	 * 
	 * @param backingMap
	 */
	public MemoryCache(Map<K, V> backingMap)
	{
		this.map = backingMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V get(K key) throws CacheException
	{
		return map.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V put(K key, V value) throws CacheException
	{
		return map.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V remove(K key) throws CacheException
	{
		return map.remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() throws CacheException
	{
		map.clear();
	}
}
