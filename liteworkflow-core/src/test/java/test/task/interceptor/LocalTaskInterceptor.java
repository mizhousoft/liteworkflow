package test.task.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;

/**
 * @author yuqs
 * @since 1.0
 */
public class LocalTaskInterceptor implements SnakerInterceptor
{
	private static final Logger log = LoggerFactory.getLogger(LocalTaskInterceptor.class);

	public void intercept(Execution execution)
	{
		if (log.isInfoEnabled())
		{
			log.info("LocalTaskInterceptor start...");
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
			log.info("LocalTaskInterceptor finish...");
		}
	}

}
