package com.liteworkflow.engine;

import org.springframework.context.ApplicationContext;

import com.liteworkflow.engine.cache.CacheManager;

/**
 * TODO
 *
 * @version
 */
public interface ProcessEngineConfiguration
{
	ProcessEngine buildProcessEngine() throws Exception;

	ApplicationContext getApplicationContext();

	CacheManager getCacheManager();

	ProcessService getProcessService();

	QueryService getQueryService();

	OrderService getOrderService();

	TaskService getTaskService();
	
	HistoryService getHistoryService();

	ManagerService getManagerService();
}
