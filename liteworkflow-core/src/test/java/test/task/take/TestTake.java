package test.task.take;

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
public class TestTake extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/task/take/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println("order=" + order);

		List<Task> tasks = queryService.getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			// engine.executeTask(task.getId(), "1");
			engine.getTaskService().take(task.getId(), "1");
		}
	}
}
