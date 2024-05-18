package test.task.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Order;
import org.snaker.engine.helper.StreamHelper;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestConfig extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();
	
		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/task/config/process.snaker"));
	}

	@Test
	public void test()
	{
		Order order = engine.startInstanceByName("config", 0, "2", null);
		System.out.println("order=" + order);
	}
}
