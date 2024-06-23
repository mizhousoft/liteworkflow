package test.task.assignmenthandler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestAssignmentHandler extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/assignmenthandler/process.xml"));
	}

	@Test
	public void test()
	{
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2");
		System.out.println("instance=" + instance);
		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().executeTask(task.getId(), "admin");
		}
	}
}
