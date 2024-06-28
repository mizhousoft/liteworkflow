package test.subprocess;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * 测试子流程的fork-join流程
 * start->subprocess1----->end
 * |___subprocess2_______|
 * 
 * @author
 * @since 1.0
 */
public class TestSubProcess2 extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream1 = TestSubProcess2.class.getClassLoader().getResourceAsStream("test/subprocess/child.xml");
		        InputStream istream2 = TestSubProcess2.class.getClassLoader().getResourceAsStream("test/subprocess/subprocess2.xml"))
		{
			engine.getRepositoryService().deploy(istream1);
			processId = engine.getRepositoryService().deploy(istream2);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2", args);
		System.out.println(instance);
		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().executeTask(task.getId(), "1", args);
		}
	}
}
