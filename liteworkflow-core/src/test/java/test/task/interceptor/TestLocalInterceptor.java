package test.task.interceptor;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.task.entity.Task;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestLocalInterceptor extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/interceptor/process.snaker"));
	}

	@Test
	public void test()
	{
		ProcessInstance order = engine.getRuntimeService().startInstanceById(processId, "2");
		System.out.println("order=" + order);
		List<Task> tasks = engine.getTaskService().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().executeTask(task.getId(), "1");
		}
	}
}
