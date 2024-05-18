package test.task.right;

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
 * 测试无权限执行任务
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestNotAllow extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/task/right/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "2" });
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println(order);
		List<Task> tasks = queryService.getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			engine.executeTask(task.getId(), SnakerEngine.ADMIN, args);
		}
	}
}
