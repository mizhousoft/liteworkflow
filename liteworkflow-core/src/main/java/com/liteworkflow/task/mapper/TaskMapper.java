package com.liteworkflow.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liteworkflow.task.entity.Task;
import com.mizhousoft.commons.mapper.PageableMapper;

/**
 * 任务持久层
 *
 * @version
 */
public interface TaskMapper extends PageableMapper<Task, String>
{
	/**
	 * 根据任务id查询任务对象
	 * 
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	Task getTask(String taskId);

	/**
	 * 根据父任务id查询所有子任务
	 * 
	 * @param parentTaskId 父任务id
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getNextActiveTasks(String parentTaskId);

	/**
	 * 根据流程实例id、任务名称获取
	 * 
	 * @param orderId 流程实例id
	 * @param taskName 任务名称
	 * @param parentTaskId 父任务id
	 * @return List<Task> 活动任务集合
	 */
	List<Task> getNextActiveTaskList(@Param("orderId") String orderId, @Param("taskName") String taskName,
	        @Param("parentTaskId") String parentTaskId);
}
