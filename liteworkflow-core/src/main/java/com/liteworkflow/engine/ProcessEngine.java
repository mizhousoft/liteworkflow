package com.liteworkflow.engine;

/**
 * 流程引擎接口
 * 
 * @author
 * @since 1.0
 */
public interface ProcessEngine
{
	String ADMIN = "snaker.admin";

	String AUTO = "snaker.auto";

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

	/**
	 * 获取管理服务
	 * 
	 * @return IManagerService 管理服务
	 */
	ManagerService getManagerService();
}
