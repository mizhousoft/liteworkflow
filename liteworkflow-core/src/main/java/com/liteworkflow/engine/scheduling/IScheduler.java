package com.liteworkflow.engine.scheduling;

/**
 * 调度器接口，与具体的定时调度框架无关
 * 
 * @author
 * @since 1.4
 */
public interface IScheduler
{
	/**
	 * 调度执行方法
	 * 
	 * @param entity 调度DTO
	 */
	void schedule(JobEntity entity);

	/**
	 * 停止调度
	 * 
	 * @param key job主键
	 */
	void delete(String key);
}
