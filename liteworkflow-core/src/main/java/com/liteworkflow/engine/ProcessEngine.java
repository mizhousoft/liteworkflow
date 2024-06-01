package com.liteworkflow.engine;

/**
 * 流程引擎接口
 * 
 * @author yuqs
 * @since 1.0
 */
public interface ProcessEngine
{
	public static final String ADMIN = "snaker.admin";

	public static final String AUTO = "snaker.auto";

	public static final String ID = "snaker.orderNo";

	/**
	 * 获取process服务
	 * 
	 * @return IProcessService 流程定义服务
	 */
	public RepositoryService getRepositoryService();

	public HistoryService getHistoryService();

	/**
	 * 获取实例服务
	 * 
	 * @return IQueryService 流程实例服务
	 */
	public ProcessInstanceService getProcessInstanceService();

	/**
	 * 获取任务服务
	 * 
	 * @return ITaskService 任务服务
	 */
	public TaskService getTaskService();

	/**
	 * 获取管理服务
	 * 
	 * @return IManagerService 管理服务
	 */
	public ManagerService getManagerService();

	public RuntimeService getRuntimeService();
}
