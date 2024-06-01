package test.surrogate;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestSurrogate extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/surrogate/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "test" });
		ProcessInstance order = engine.getRuntimeService().startInstanceByName("surrogate", 0, "2", args);
		System.out.println("order=" + order);
		// List<Task> tasks = queryService.getActiveTasks(new
		// QueryFilter().setOrderId(order.getId()));
		// for(Task task : tasks) {
		// //engine.executeTask(task.getId(), "1", args);
		// }
	}
}
