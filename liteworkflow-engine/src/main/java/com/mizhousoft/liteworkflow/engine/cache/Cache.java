package com.mizhousoft.liteworkflow.engine.cache;

/**
 * 缓存接口
 * 
 * @version
 */
public interface Cache<K, V>
{
	/**
	 * 根据key从缓存中获取对应的值
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	public V get(K key) throws CacheException;

	/**
	 * 添加缓存键值对
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws CacheException
	 */
	public V put(K key, V value) throws CacheException;

	/**
	 * 根据key从缓存中删除对象
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	public V remove(K key) throws CacheException;

	/**
	 * 从缓存中清除所有的对象
	 * 
	 * @throws CacheException
	 */
	public void clear() throws CacheException;
}
