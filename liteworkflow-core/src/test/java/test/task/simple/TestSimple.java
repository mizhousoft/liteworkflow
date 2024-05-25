package test.task.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.task.entity.Task;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestSimple extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/simple/process.snaker"));
		engine.getRepositoryService().updateType(processId, "预算管理流程");
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.getRuntimeService().startInstanceByName("simple", 0, "2", args);
		System.out.println("order=" + order);
		List<Task> tasks = engine.getTaskService().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().executeTask(task.getId(), "1", args);
		}
	}
}
