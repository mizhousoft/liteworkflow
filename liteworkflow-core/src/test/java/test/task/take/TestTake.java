package test.task.take;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestTake extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/take/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2", args);
		System.out.println("instance=" + instance);

		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			// engine.executeTask(task.getId(), "1");
			engine.getTaskService().take(task.getId(), "1");
		}
	}
}
