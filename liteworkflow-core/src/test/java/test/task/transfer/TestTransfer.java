package test.task.transfer;

import java.util.List;

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
public class TestTransfer extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/task/transfer/process.snaker"));
	}

	@Test
	public void test()
	{
		Order order = engine.startInstanceByName("transfer", 0);
		System.out.println("order=" + order);
		List<Task> tasks = engine.getTaskService().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().createNewTask(task.getId(), 0, "test");
			engine.getTaskService().complete(task.getId());
		}
	}
}
