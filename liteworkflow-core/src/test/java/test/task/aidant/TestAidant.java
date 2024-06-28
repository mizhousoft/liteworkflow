package test.task.aidant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestAidant extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestAidant.class.getClassLoader().getResourceAsStream("test/task/aidant/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("aidant", 0);
		System.out.println("instance=" + instance);
		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			// engine.executeTask(task.getId());
			engine.getTaskService().createNewTask(task.getId(), 1, "test");
		}
	}
}
