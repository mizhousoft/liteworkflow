package org.snaker.engine;

import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;

/**
 * 任务、实例完成时触发动作的接口
 * 
 * @author yuqs
 * @since 2.2.0
 */
public interface Completion
{
	/**
	 * 任务完成触发执行
	 * 
	 * @param task 任务对象
	 */
	public void complete(HistoryTask task);

	/**
	 * 实例完成触发执行
	 * 
	 * @param order 实例对象
	 */
	public void complete(HistoryOrder order);
}
