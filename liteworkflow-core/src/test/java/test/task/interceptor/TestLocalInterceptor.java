package test.task.interceptor;

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
public class TestLocalInterceptor extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestLocalInterceptor.class.getClassLoader().getResourceAsStream("test/task/interceptor/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2");
		System.out.println("instance=" + instance);
		List<Task> tasks = engine.getTaskService().queryByInstanceId(instance.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().complete(task.getId(), "1");
		}
	}
}
