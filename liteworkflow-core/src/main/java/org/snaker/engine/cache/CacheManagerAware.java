package org.snaker.engine.cache;

/**
 * 该接口的实现类，需要设置cache管理器
 * 
 * @author yuqs
 * @since 1.3
 */
public interface CacheManagerAware
{
	/**
	 * 设置cache管理器
	 * 
	 * @param cacheManager
	 */
	void setCacheManager(CacheManager cacheManager);
}
