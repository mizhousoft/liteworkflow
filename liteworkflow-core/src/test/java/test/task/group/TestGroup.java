package test.task.group;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * 测试该类时，确认是否配置了自定义的访问策略
 * 
 * @author
 * @since 1.4
 */
public class TestGroup extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestGroup.class.getClassLoader().getResourceAsStream("test/task/group/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		try
		{
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("task1.operator", new String[] { "role1" });
			ProcessInstance instance = engine.getRuntimeService().startInstanceByName("group", "2", args);
			System.out.println("instance=" + instance);
			List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
			for (Task task : tasks)
			{
				// 操作人改为test时，角色对应test，会无权处理
				engine.getTaskService().executeTask(task.getId(), "test1", args);
			}

			Assertions.assertTrue(true);
		}
		catch (WorkFlowException e)
		{
			Assertions.fail();
		}
	}
}
