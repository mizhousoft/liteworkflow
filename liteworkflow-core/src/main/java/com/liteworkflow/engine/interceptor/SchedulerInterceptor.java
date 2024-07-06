package com.liteworkflow.engine.interceptor;

import java.time.LocalDate;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.scheduling.IScheduler;
import com.liteworkflow.engine.scheduling.JobEntity;
import com.liteworkflow.engine.scheduling.JobEntity.JobType;

/**
 * 时限控制拦截器
 * 主要拦截任务的expireDate(期望完成时间)
 * 再交给具体的调度器完成调度处理
 * 
 * @author
 * @since 1.4
 */
public class SchedulerInterceptor implements FlowInterceptor
{
	private static final Logger log = LoggerFactory.getLogger(SchedulerInterceptor.class);

	/**
	 * 调度器接口
	 */
	private IScheduler scheduler;

	/**
	 * 是否调度
	 */
	private boolean isScheduled = false;

	/**
	 * 时限控制拦截方法
	 */
	public void intercept(Execution execution)
	{
		if (!isScheduled)
			return;
		for (Task task : execution.getTasks())
		{
			String id = execution.getProcessDefinition().getId() + "-" + execution.getProcessInstance().getId() + "-" + task.getId();
			LocalDate expireDate = task.getExpireDate();
			if (expireDate != null)
			{
				schedule(id, task, expireDate, JobType.EXECUTER.ordinal(), execution.getArgs());
			}
			LocalDate remindDate = task.getRemindDate();
			if (remindDate != null)
			{
				schedule(id, task, remindDate, JobType.REMINDER.ordinal(), execution.getArgs());
			}
		}
	}

	public void schedule(String id, Task task, LocalDate startDate, int jobType, Map<String, Object> args)
	{
		try
		{
			JobEntity entity = new JobEntity(id, task, startDate, args);
			entity.setModelName(task.getName());
			entity.setJobType(jobType);
			if (jobType == JobType.REMINDER.ordinal())
			{
				TaskModel model = (TaskModel) task.getModel();
				if (model != null && NumberUtils.isCreatable(model.getReminderRepeat()))
				{
					entity.setPeriod(Integer.parseInt(model.getReminderRepeat()));
				}
			}
			schedule(entity);
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			log.info("scheduler failed.task is:" + task);
		}
	}

	private void schedule(JobEntity entity)
	{

	}
}
