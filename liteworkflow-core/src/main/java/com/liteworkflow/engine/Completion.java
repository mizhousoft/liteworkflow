package com.liteworkflow.engine;

import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;

/**
 * 任务、实例完成时触发动作的接口
 * 
 * @author
 * @since 2.2.0
 */
public interface Completion
{
	/**
	 * 任务完成触发执行
	 * 
	 * @param task 任务对象
	 */
	public void complete(HistoricTask task);

	/**
	 * 实例完成触发执行
	 * 
	 * @param historicInstance 实例对象
	 */
	public void complete(HistoricProcessInstance historicInstance);
}
