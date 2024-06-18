package test.task.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

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
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("config", 0, "2", null);
		System.out.println("instance=" + instance);
	}
}
