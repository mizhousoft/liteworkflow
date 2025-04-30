package com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.Execution;
import com.mizhousoft.liteworkflow.engine.impl.Expression;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.el.SpelExpression;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.util.HistoricTaskUtils;
import com.mizhousoft.liteworkflow.engine.impl.util.TaskUtils;

/**
 * 用户任务流程执行器
 *
 * @version
 */
public class UserTaskActivityBehavior extends NodeActivityBehavior
{
	/**
	 * 模型
	 */
	private UserTaskModel taskModel;

	/**
	 * 构造函数
	 *
	 * @param taskModel
	 */
	public UserTaskActivityBehavior(UserTaskModel taskModel)
	{
		super();
		this.taskModel = taskModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Execution execution)
	{
		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();

		ProcessInstanceEntity processInstance = execution.getProcessInstance();

		TaskEntity task = TaskUtils.buildTask(taskModel, processInstance);
		assignTaskAssignee(task, taskModel, execution);

		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();
		taskEntityService.addEntity(task);

		String oldAssignee = task.getAssignee();

		ListenerInvocation listenerInvocation = engineConfiguration.getListenerInvocation();
		listenerInvocation.executeTaskListeners(taskModel, task, TaskListener.EVENTNAME_CREATE);

		if (!StringUtils.equals(oldAssignee, task.getAssignee()))
		{
			taskEntityService.modifyEntity(task);
		}

		HistoricTaskUtils.createHistoricTask(task, engineConfiguration);

		return true;
	}

	/**
	 * 分配任务执行人
	 * 
	 * @param task
	 * @param taskModel
	 * @param execution
	 */
	private void assignTaskAssignee(TaskEntity task, UserTaskModel taskModel, Execution execution)
	{
		String assignee = taskModel.getAssignee();
		if (null == assignee)
		{
			return;
		}

		if (assignee.contains("#"))
		{
			ApplicationContext applicationContext = execution.getEngineConfiguration().getApplicationContext();
			Expression expression = new SpelExpression(applicationContext);

			String value = expression.eval(String.class, assignee, execution.getArgs());
			if (null == value)
			{
				String message = "Unknown property used in expression: %s with ProcessInstance(%s).";
				throw new WorkFlowException(String.format(message, assignee, execution.getProcessInstance().getId()));
			}

			assignee = value;
		}

		task.setAssignee(assignee);
	}
}
