package com.liteworkflow.engine.scheduling;

import java.util.Map;

import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;

/**
 * 提醒接口
 * 
 * @author
 * @since 2.0
 */
public interface IReminder
{
	/**
	 * 提醒操作
	 * 
	 * @param process 流程定义对象
	 * @param instanceId 流程实例id
	 * @param taskId 任务id
	 * @param nodeModel 节点模型
	 * @param data 数据
	 */
	void remind(ProcessDefinition process, String instanceId, String taskId, NodeModel nodeModel, Map<String, Object> data);
}
