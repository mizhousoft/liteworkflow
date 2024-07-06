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
	 * @param instanceId 历史流程实例id
	 * @return HistoricProcessInstance 历史流程实例对象
	 */
	HistoricProcessInstance getHistoricInstance(String instanceId);

	/**
	 * 根据filter分页查询历史流程实例
	 * 
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<HistoricProcessInstance> 历史实例集合
	 */
	Page<HistoricProcessInstance> queryPageData(HistoricInstancePageRequest request);

	/**
	 * 根据任务ID获取历史任务对象
	 * 
	 * @param id
	 * @return
	 */
	HistoricTask getHistTask(String id);

	/**
	 * 根据流程实例ID查询历史任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<HistoricTask> queryHistoricTasks(String instanceId);

	/**
	 * 分页查询历史任务
	 * 
	 * @param request
	 * @return
	 */
	Page<HistoricTask> queryPageData(TaskPageRequest request);
}
