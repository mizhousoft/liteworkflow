package com.mizhousoft.liteworkflow.engine;

/**
 * 流程引擎接口
 * 
 * @version
 */
public interface ProcessEngine
{
	/**
	 * 获取RepositoryService服务
	 * 
	 * @return
	 */
	RepositoryService getRepositoryService();

	/**
	 * 获取ProcessInstanceService服务
	 * 
	 * @return
	 */
	ProcessInstanceService getProcessInstanceService();

	/**
	 * 获取RuntimeService服务
	 * 
	 * @return
	 */
	RuntimeService getRuntimeService();

	/**
	 * 获取TaskService服务
	 * 
	 * @return
	 */
	TaskService getTaskService();

	/**
	 * 获取HistoryService服务
	 * 
	 * @return
	 */
	HistoryService getHistoryService();
}
