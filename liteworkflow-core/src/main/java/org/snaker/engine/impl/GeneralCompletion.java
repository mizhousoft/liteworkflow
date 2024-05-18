package org.snaker.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.Completion;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;

/**
 * 默认的任务、实例完成时触发的动作
 * 
 * @author yuqs
 * @since 2.2.0
 */
public class GeneralCompletion implements Completion
{
	private static final Logger log = LoggerFactory.getLogger(GeneralCompletion.class);

	public void complete(HistoryTask task)
	{
		log.info("The task[{}] has been user[{}] has completed", task.getId(), task.getOperator());
	}

	public void complete(HistoryOrder order)
	{
		log.info("The order[{}] has completed", order.getId());
	}
}
