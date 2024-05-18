package com.liteworkflow.engine.cache;

/**
 * 缓存管理器接口，该接口提供具体的cache实现
 * 
 * @author yuqs
 * @since 1.3
 */
public interface CacheManager
{
	/**
	 * 根据cache的名称获取cache。如果不存在，默认新建并返回
	 * 
	 * @param name
	 * @return Cache
	 * @throws CacheException
	 */
	public <K, V> Cache<K, V> getCache(String name) throws CacheException;

	/**
	 * 销毁cache
	 * 
	 * @throws CacheException
	 */
	public void destroy() throws CacheException;
}
