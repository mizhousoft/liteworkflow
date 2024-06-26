package com.liteworkflow.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;

/**
 * 默认的任务、实例完成时触发的动作
 * 
 * @author
 * @since 2.2.0
 */
public class GeneralCompletion implements Completion
{
	private static final Logger log = LoggerFactory.getLogger(GeneralCompletion.class);

	public void complete(HistoricTask task)
	{
		log.info("The task[{}] has been user[{}] has completed", task.getId(), task.getOperator());
	}

	public void complete(HistoricProcessInstance historicInstance)
	{
		log.info("The HistoricProcessInstance[{}] has completed", historicInstance.getId());
	}
}
