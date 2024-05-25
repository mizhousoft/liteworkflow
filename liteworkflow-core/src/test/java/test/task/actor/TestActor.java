package test.task.actor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestActor extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();
	}

	@Test
	public void test()
	{
		// engine.getTaskService().addTaskActor("13b9edb451e5453394f7980ff4ab7ca9", new String[] { "test1",
		// "test2" });
		engine.getTaskService().removeTaskActor("13b9edb451e5453394f7980ff4ab7ca9", "2");
	}
}
