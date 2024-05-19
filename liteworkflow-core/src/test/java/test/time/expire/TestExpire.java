package test.time.expire;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestExpire extends TestSpring
{
	private static final String PROCESSNAME = "expire";

	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		engine.process().deploy(StreamHelper.getStreamFromClasspath("test/time/expire/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		args.put("task1.expireTime", LocalDateTimeUtils.toDate(LocalDateTime.of(2014, 4, 15, 9, 0)));
		args.put("task1.reminderTime", LocalDateTimeUtils.toDate(LocalDateTime.of(2014, 4, 15, 8, 57)));
		Order order = engine.startInstanceByName(PROCESSNAME, null, "2", args);
		System.out.println("order=" + order);
		// List<Task> tasks = queryService.getActiveTasks(new
		// QueryFilter().setOrderId(order.getId()));
		// for(Task task : tasks) {
		// engine.executeTask(task.getId(), "1", args);
		// }
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
