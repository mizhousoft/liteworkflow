package test.task.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.interceptor.FlowInterceptor;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * @author
 * @since 1.0
 */
public class LocalTaskInterceptor implements FlowInterceptor
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
				buffer.append("}]");
				log.info(buffer.toString());
			}
			log.info("LocalTaskInterceptor finish...");
		}
	}

}
