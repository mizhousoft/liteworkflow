package test.concurrency.actorall;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestActorAll extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/concurrency/actorall/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1", "2" });
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println(order);
		// Assert.assertEquals(2, tasks.size());
		// execute(tasks, args);
	}
}
