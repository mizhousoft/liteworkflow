package test.task.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

import test.TestSpring;

/**
 * 测试模型操作
 * 
 * @author
 * @since 2.0
 */
public class TestModel extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/task/simple/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("simple", null, "2", args);
		System.out.println("instance=" + instance);

		ProcessDefinition process = engine.getRepositoryService().getProcessById(instance.getProcessId());
		ProcessModel processModel = process.getModel();

		List<Task> tasks = engine.getTaskService().getActiveTasks(instance.getId());
		for (Task task : tasks)
		{
			TaskModel model = getTaskModel(task.getTaskName(), processModel);
			System.out.println(model.getName());
			List<TaskModel> models = model.getNextModels(TaskModel.class);
			for (TaskModel tm : models)
			{
				System.out.println(tm.getName());
			}
		}
		List<TaskModel> models = engine.getRepositoryService().getProcessById(processId).getModel().getModels(TaskModel.class);
		for (TaskModel tm : models)
		{
			System.out.println(tm.getName());
		}
	}

	public TaskModel getTaskModel(String taskName, ProcessModel processModel)
	{
		NodeModel nodeModel = processModel.getNode(taskName);
		AssertHelper.notNull(nodeModel, "任务id无法找到节点模型.");
		if (nodeModel instanceof TaskModel)
		{
			return (TaskModel) nodeModel;
		}
		else
		{
			throw new IllegalArgumentException("任务id找到的节点模型不匹配");
		}
	}

}
