package test.freeflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.task.entity.Task;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestFreeFlow extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/freeflow/free.snaker"));
	}

	// @Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.getRuntimeService().startInstanceById(processId, "2", args);
		// System.out.println("order=" + order);
		TaskModel tm1 = new TaskModel();
		tm1.setName("task1");
		tm1.setDisplayName("任务1");
		TaskModel tm2 = new TaskModel();
		tm2.setName("task2");
		tm2.setDisplayName("任务2");
		List<Task> tasks = null;
		tasks = engine.getTaskService().createFreeTask(order.getId(), "1", args, tm1);
		for (Task task : tasks)
		{
			engine.getTaskService().complete(task.getId(), "1", null);
		}

		// tasks = engine.createFreeTask(order.getId(), "1", args, tm2);
		// for(Task task : tasks) {
		// engine.getTaskService().complete(task.getId(), "1", null);
		// }
		engine.getOrderService().terminate(order.getId());
	}
}
