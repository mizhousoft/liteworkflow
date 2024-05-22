package test.custom;

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
public class TestCustomHandler extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/custom/snaker1.snaker"));
	}

	@Test
	public void test()
	{
		Order order = engine.startInstanceById(processId);
		System.out.println("order=" + order);
		List<Task> tasks = queryService.getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.executeTask(task.getId());
		}
	}
}
