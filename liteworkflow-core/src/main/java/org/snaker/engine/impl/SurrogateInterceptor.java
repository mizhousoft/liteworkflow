package org.snaker.engine.impl;

import org.snaker.engine.TaskService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.StringHelper;

/**
 * 委托代理拦截器
 * 负责查询wf_surrogate表获取委托代理人，并通过addTaskActor设置为参与者
 * 这里是对新创建的任务通过添加参与者进行委托代理(即授权人、代理人都可处理任务)
 * 对于运行中且未处理的待办任务，可调用engine.task().addTaskActor方法
 * {@link TaskService#addTaskActor(String, String...)}
 * 
 * @author yuqs
 * @since 1.4
 */
public class SurrogateInterceptor implements SnakerInterceptor
{
	public void intercept(Execution execution)
	{
		SnakerEngine engine = execution.getEngine();
		for (Task task : execution.getTasks())
		{
			if (task.getActorIds() == null)
				continue;
			for (String actor : task.getActorIds())
			{
				if (StringHelper.isEmpty(actor))
					continue;
				String agent = engine.manager().getSurrogate(actor, execution.getProcess().getName());
				if (StringHelper.isNotEmpty(agent) && !actor.equals(agent))
				{
					engine.task().addTaskActor(task.getId(), agent);
				}
			}
		}
	}

}
