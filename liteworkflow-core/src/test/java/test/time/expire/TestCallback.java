package test.time.expire;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.scheduling.JobCallback;
import com.liteworkflow.task.entity.Task;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestCallback implements JobCallback
{
	private static final Logger log = LoggerFactory.getLogger(TestCallback.class);

	public void callback(String taskId, List<Task> newTasks)
	{
		log.info("callback taskId=" + taskId);
		log.info("newTasks=" + newTasks);
	}
}
