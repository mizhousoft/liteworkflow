package com.mizhousoft.liteworkflow.engine;

import org.springframework.context.ApplicationContext;

import com.mizhousoft.liteworkflow.engine.cache.CacheManager;

/**
 * 工作流引擎配置类
 *
 * @version
 */
public interface ProcessEngineConfiguration
{
	/**
	 * 构建工作流引擎
	 * 
	 * @return
	 * @throws Exception
	 */
	ProcessEngine buildProcessEngine() throws Exception;

	/**
	 * 获取ApplicationContext
	 * 
	 * @return
	 */
	ApplicationContext getApplicationContext();

	/**
	 * 获取缓存管理器
	 * 
	 * @return
	 */
	CacheManager getCacheManager();

	/**
	 * 获取RepositoryService
	 * 
	 * @return
	 */
	RepositoryService getRepositoryService();

	/**
	 * 获取RuntimeService
	 * 
	 * @return
	 */
	RuntimeService getRuntimeService();

	/**
	 * 获取ProcessInstanceService
	 * 
	 * @return
	 */
	ProcessInstanceService getProcessInstanceService();

	/**
	 * 获取TaskService
	 * 
	 * @return
	 */
	TaskService getTaskService();

	/**
	 * 获取HistoryService
	 * 
	 * @return
	 */
	HistoryService getHistoryService();
}
