package com.liteworkflow.engine.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * 委托代理拦截器
 * 负责查询wf_surrogate表获取委托代理人，并通过addTaskActor设置为参与者
 * 这里是对新创建的任务通过添加参与者进行委托代理(即授权人、代理人都可处理任务)
 * 对于运行中且未处理的待办任务，可调用engine.getTaskService().addTaskActor方法
 * {@link TaskService#addTaskActor(String, String...)}
 * 
 * @author
 * @since 1.4
 */
public class SurrogateInterceptor implements FlowInterceptor
{
	public void intercept(Execution execution)
	{
		ProcessEngineConfiguration engine = execution.getEngineConfiguration();
		for (Task task : execution.getTasks())
		{
			if (task.getActorIds() == null)
				continue;
			for (String actor : task.getActorIds())
			{
				if (StringUtils.isBlank(actor))
					continue;
				String agent = engine.getManagerService().getSurrogate(actor, execution.getProcessDefinition().getName());
				if (!StringUtils.isBlank(agent) && !actor.equals(agent))
				{
					engine.getTaskService().addTaskActor(task.getId(), agent);
				}
			}
		}
	}

}
