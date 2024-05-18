package com.liteworkflow.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.SnakerInterceptor;
import com.liteworkflow.engine.core.Execution;
import com.liteworkflow.engine.entity.Task;

/**
 * 日志拦截器
 * 
 * @author yuqs
 * @since 1.0
 */
public class LogInterceptor implements SnakerInterceptor
{
	private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

	/**
	 * 拦截产生的任务对象，打印日志
	 */
	public void intercept(Execution execution)
	{
		if (log.isInfoEnabled())
		{
			for (Task task : execution.getTasks())
			{
				StringBuffer buffer = new StringBuffer(100);
				buffer.append("创建任务[标识=").append(task.getId());
				buffer.append(",名称=").append(task.getDisplayName());
				buffer.append(",创建时间=").append(task.getCreateTime());
				buffer.append(",参与者={");
				if (task.getActorIds() != null)
				{
					for (String actor : task.getActorIds())
					{
						buffer.append(actor).append(";");
					}
				}
				buffer.append("}]");
				log.info(buffer.toString());
			}
		}
	}
}
