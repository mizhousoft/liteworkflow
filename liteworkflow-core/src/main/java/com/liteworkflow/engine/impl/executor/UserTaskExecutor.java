package com.liteworkflow.engine.impl.executor;

import java.time.LocalDateTime;
import java.util.Map;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.impl.Expression;
import com.liteworkflow.engine.impl.el.SpelExpression;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.mizhousoft.commons.json.JSONUtils;

/**
 * 用户任务流程执行器
 *
 * @version
 */
public class UserTaskExecutor extends NodeFlowExecutor
{
	/**
	 * 表达式解析器
	 */
	private Expression expression = new SpelExpression();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean doExecute(Execution execution, FlowNode nodeModel)
	{
		UserTaskModel taskModel = (UserTaskModel) nodeModel;

		ProcessEngineConfigurationImpl engineConfiguration = execution.getEngineConfiguration();
		TaskEntityService taskEntityService = engineConfiguration.getTaskEntityService();

		String assignee = obtainTaskAssignee(taskModel, execution);

		Task task = createTask(taskModel, execution, assignee);
		taskEntityService.addEntity(task);

		return true;
	}

	/**
	 * 获取任务执行人
	 * 
	 * @param taskModel
	 * @param execution
	 * @return
	 */
	private String obtainTaskAssignee(UserTaskModel taskModel, Execution execution)
	{
		String assignee = taskModel.getAssignee();
		if (null == assignee)
		{
			return null;
		}

		if (assignee.startsWith("${"))
		{
			String expr = "#" + assignee.substring(2, assignee.length() - 1);

			String value = expression.eval(String.class, expr, execution.getArgs());
			if (null == value)
			{
				String message = "Unknown property used in expression: %s with ProcessInstance(%s).";
				throw new WorkFlowException(String.format(message, assignee, execution.getProcessInstance().getId()));
			}

			return value;
		}
		else
		{
			return assignee;
		}
	}

	/**
	 * 创建任务
	 * 
	 * @param userTask
	 * @param execution
	 * @param assignee
	 * @return
	 */
	private Task createTask(UserTaskModel userTask, Execution execution, String assignee)
	{
		Task task = new Task();
		task.setParentTaskId(execution.getTask() == null ? 0 : execution.getTask().getId());
		task.setProcessDefinitionId(execution.getProcessInstance().getProcessDefinitionId());
		task.setInstanceId(execution.getProcessInstance().getId());
		task.setTaskDefinitionId(userTask.getId());
		task.setName(userTask.getName());
		task.setAssignee(assignee);
		task.setTaskType(0);
		task.setCreateTime(LocalDateTime.now());

		Map<String, Object> args = execution.getArgs();
		task.setVariable(JSONUtils.toJSONStringQuietly(args));

		return task;
	}
}
