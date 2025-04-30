package com.mizhousoft.liteworkflow.engine.impl.delegate.invocation;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.mizhousoft.commons.lang.ClassUtils;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.EventListenerElement;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.delegate.ExecutionListener;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.Expression;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.el.SpelExpression;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessInstanceEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.TaskEntity;

/**
 * 监听器执行
 *
 * @version
 */
public class ListenerInvocationImpl implements ListenerInvocation
{
	/**
	 * ProcessEngineConfigurationImpl
	 */
	private ProcessEngineConfigurationImpl engineConfiguration;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public ListenerInvocationImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super();
		this.engineConfiguration = engineConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeExecutionListeners(BpmnModel bpmnModel, ProcessInstanceEntity processInstance, String eventType)
	{
		try
		{
			List<EventListenerElement> listenerElements = bpmnModel.getEventListeners();
			for (EventListenerElement listenerElement : listenerElements)
			{
				if (!eventType.equals(listenerElement.getEvent()))
				{
					continue;
				}

				ExecutionListener executionListener = createExecutionListener(listenerElement);
				if (null == executionListener)
				{
					continue;
				}

				if (ExecutionListener.EVENTNAME_START.equals(eventType))
				{
					executionListener.processStarted(processInstance);
				}
				else if (ExecutionListener.EVENTNAME_END.equals(eventType))
				{
					executionListener.processCompleted(processInstance);
				}
				else if (ExecutionListener.EVENTNAME_CANCELLED.equals(eventType))
				{
					executionListener.processCancelled(processInstance);
				}
			}
		}
		catch (Exception e)
		{
			throw new WorkFlowException("ExecutionListener execute failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeTaskListeners(UserTaskModel taskModel, TaskEntity task, String eventType)
	{
		try
		{
			List<EventListenerElement> listenerElements = taskModel.getEventListeners();
			for (EventListenerElement listenerElement : listenerElements)
			{
				if (!eventType.equals(listenerElement.getEvent()))
				{
					continue;
				}

				TaskListener taskListener = createTaskListener(listenerElement);
				if (null == taskListener)
				{
					continue;
				}

				if (TaskListener.EVENTNAME_CREATE.equals(eventType) || TaskListener.EVENTNAME_ALL_EVENTS.equals(eventType))
				{
					taskListener.taskCreated(task);
				}
				else if (TaskListener.EVENTNAME_ASSIGNMENT.equals(eventType) || TaskListener.EVENTNAME_ALL_EVENTS.equals(eventType))
				{
					taskListener.taskAssigned(task);
				}
				else if (TaskListener.EVENTNAME_DELETE.equals(eventType) || TaskListener.EVENTNAME_ALL_EVENTS.equals(eventType))
				{
					taskListener.taskDeleted(task);
				}
				else if (TaskListener.EVENTNAME_COMPLETE.equals(eventType) || TaskListener.EVENTNAME_ALL_EVENTS.equals(eventType))
				{
					taskListener.taskCompleted(task);
				}
			}
		}
		catch (Exception e)
		{
			throw new WorkFlowException("TaskListener execute failed.", e);
		}
	}

	/**
	 * 创建任务监听器
	 * 
	 * @param listenerElement
	 * @return
	 * @throws Exception
	 */
	private TaskListener createTaskListener(EventListenerElement listenerElement) throws Exception
	{
		TaskListener taskListener = null;

		if (EventListenerElement.IMPLEMENTATION_TYPE_CLASS.equals(listenerElement.getImplementationType()))
		{
			taskListener = (TaskListener) ClassUtils.newInstance(listenerElement.getImplementation(), this.getClass().getClassLoader());
		}
		else if (EventListenerElement.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equals(listenerElement.getImplementationType()))
		{
			ApplicationContext applicationContext = engineConfiguration.getApplicationContext();
			Expression expression = new SpelExpression(applicationContext);

			taskListener = expression.eval(TaskListener.class, listenerElement.getImplementation(), Collections.emptyMap());
		}

		return taskListener;
	}

	/**
	 * 创建执行器监听器
	 * 
	 * @param listenerElement
	 * @return
	 * @throws Exception
	 */
	private ExecutionListener createExecutionListener(EventListenerElement listenerElement) throws Exception
	{
		ExecutionListener executionListener = null;

		if (EventListenerElement.IMPLEMENTATION_TYPE_CLASS.equals(listenerElement.getImplementationType()))
		{
			executionListener = (ExecutionListener) ClassUtils.newInstance(listenerElement.getImplementation(),
			        this.getClass().getClassLoader());
		}
		else if (EventListenerElement.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equals(listenerElement.getImplementationType()))
		{
			ApplicationContext applicationContext = engineConfiguration.getApplicationContext();
			Expression expression = new SpelExpression(applicationContext);

			executionListener = expression.eval(ExecutionListener.class, listenerElement.getImplementation(), Collections.emptyMap());
		}

		return executionListener;
	}

}
