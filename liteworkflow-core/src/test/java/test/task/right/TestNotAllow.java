package test.task.right;

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
 * 测试无权限执行任务
 * 
 * @author
 * @since 1.0
 */
public class TestNotAllow extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestNotAllow.class.getClassLoader().getResourceAsStream("test/task/right/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "2" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, "2", args);
		System.out.println(instance);
		List<Task> tasks = engine.getTaskService().queryByInstanceId(instance.getId());
		for (Task task : tasks)
		{
			engine.getTaskService().complete(task.getId(), ProcessEngine.ADMIN, args);
		}
	}
}
