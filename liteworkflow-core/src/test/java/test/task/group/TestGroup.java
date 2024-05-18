package test.task.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.SnakerException;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.StreamHelper;

import test.TestSpring;

/**
 * 测试该类时，确认是否配置了自定义的访问策略，请检查snaker.xml中的配置
 * 
 * @author yuqs
 * @since 1.4
 */
public class TestGroup extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/task/group/process.snaker"));
	}

	@Test
	public void test()
	{
		try
		{
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("task1.operator", new String[] { "role1" });
			Order order = engine.startInstanceByName("group", 0, "2", args);
			System.out.println("order=" + order);
			List<Task> tasks = queryService.getActiveTasks(order.getId());
			for (Task task : tasks)
			{
				// 操作人改为test时，角色对应test，会无权处理
				engine.executeTask(task.getId(), "test1", args);
			}

			Assertions.fail();
		}
		catch (SnakerException e)
		{
			Assertions.assertTrue(true);
		}
	}
}
