package com.mizhousoft.liteworkflow.engine.cache.memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cache.Cache;
import com.mizhousoft.liteworkflow.engine.cache.CacheException;
import com.mizhousoft.liteworkflow.engine.cache.CacheManager;

/**
 * 基于虚拟机内存的cache管理器
 * 
 * @version
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MemoryCacheManager implements CacheManager
{
	/**
	 * 缓存对象
	 */
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>(10);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException
	{
		if (StringUtils.isBlank(name))
		{
			throw new WorkFlowException("Cache name is null.");
		}

		Cache cache = caches.get(name);
		if (cache == null)
		{
			cache = new MemoryCache<String, Object>(new ConcurrentHashMap<String, Object>());
			Cache existing = caches.putIfAbsent(name, cache);
			if (existing != null)
			{
				cache = existing;
			}
		}

		return cache;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() throws CacheException
	{
		while (!caches.isEmpty())
		{
			caches.clear();
		}
	}
}
