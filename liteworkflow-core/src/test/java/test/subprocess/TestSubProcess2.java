package test.subprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.engine.entity.Order;
import com.liteworkflow.engine.entity.Task;
import com.liteworkflow.engine.helper.StreamHelper;

import test.TestSpring;

/**
 * 测试子流程的fork-join流程
 * start->subprocess1----->end
 * |___subprocess2_______|
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestSubProcess2 extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		engine.process().deploy(StreamHelper.getStreamFromClasspath("test/subprocess/child.snaker"));
		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/subprocess/subprocess2.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println(order);
		List<Task> tasks = engine.query().getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.executeTask(task.getId(), "1", args);
		}
	}
}
