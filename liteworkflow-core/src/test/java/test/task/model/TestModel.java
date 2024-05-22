package test.task.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.task.entity.Task;

import test.TestSpring;

/**
 * 测试模型操作
 * 
 * @author yuqs
 * @since 2.0
 */
public class TestModel extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.process();
		queryService = engine.query();

		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/task/simple/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.startInstanceByName("simple", null, "2", args);
		System.out.println("order=" + order);
		List<Task> tasks = queryService.getActiveTasks(order.getId());
		for (Task task : tasks)
		{
			TaskModel model = engine.task().getTaskModel(task.getId());
			System.out.println(model.getName());
			List<TaskModel> models = model.getNextModels(TaskModel.class);
			for (TaskModel tm : models)
			{
				System.out.println(tm.getName());
			}
		}
		List<TaskModel> models = engine.process().getProcessById(processId).getModel().getModels(TaskModel.class);
		for (TaskModel tm : models)
		{
			System.out.println(tm.getName());
		}
	}

}
