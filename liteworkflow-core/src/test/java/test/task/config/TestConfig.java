package test.task.config;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestConfig extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestConfig.class.getClassLoader().getResourceAsStream("test/task/config/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("config", "2", null);
		System.out.println("instance=" + instance);
	}
}
