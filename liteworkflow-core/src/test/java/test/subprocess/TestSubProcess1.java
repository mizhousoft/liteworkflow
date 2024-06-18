package test.subprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * 测试简单的子流程
 * start->task1->subprocess1->end
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestSubProcess1 extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/subprocess/child.snaker"));
		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/subprocess/subprocess1.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2", args);
		System.out.println("************************" + instance);

		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			System.out.println("************************begin:::::" + task);
			engine.getTaskService().executeTask(task.getId(), "1", args);
			System.out.println("************************end:::::" + task);
		}
	}
}
