package test.task.config;

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
public class TestConfig extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/config/process.snaker"));
	}

	@Test
	public void test()
	{
		Order order = engine.getRuntimeService().startInstanceByName("config", 0, "2", null);
		System.out.println("order=" + order);
	}
}
