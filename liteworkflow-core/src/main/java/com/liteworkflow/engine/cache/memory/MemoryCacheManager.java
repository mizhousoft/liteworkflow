package com.liteworkflow.engine.cache.memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.liteworkflow.engine.cache.Cache;
import com.liteworkflow.engine.cache.CacheException;
import com.liteworkflow.engine.cache.CacheManager;

/**
 * 基于虚拟机内存的cache管理器
 * 
 * @author
 * @since 1.3
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MemoryCacheManager implements CacheManager
{
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>(10);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException
	{
		if (StringUtils.isBlank(name))
		{
			throw new IllegalArgumentException("Cache name is null.");
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
