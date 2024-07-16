package com.liteworkflow.engine;

import java.util.List;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 历史数据服务
 *
 * @version
 */
public interface HistoryService
{
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * 
	 * @param instanceId
	 * @return
	 */
	HistoricProcessInstance getHistoricInstance(int instanceId);

	/**
	 * 分页查询历史流程实例
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param taskId
	 * @return
	 */
	HistoricTask getHistoricTask(int taskId);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryHistoricTasks(int instanceId);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricTask> queryPageData(TaskPageRequest request);
}
