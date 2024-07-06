package test.task.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.liteworkflow.engine.ProcessEngine;
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
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestModel.class.getClassLoader().getResourceAsStream("test/task/simple/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("simple", "2", args);
		System.out.println("instance=" + instance);

		ProcessDefinition process = engine.getRepositoryService().getById(instance.getProcessDefinitionId());
		ProcessModel processModel = process.getModel();

		List<Task> tasks = engine.getTaskService().queryByInstanceId(instance.getId());
		for (Task task : tasks)
		{
			TaskModel model = getTaskModel(task.getName(), processModel);
			System.out.println(model.getName());
			List<TaskModel> models = model.getNextModels(TaskModel.class);
			for (TaskModel tm : models)
			{
				System.out.println(tm.getName());
			}
		}
		List<TaskModel> models = engine.getRepositoryService().getById(processId).getModel().getModels(TaskModel.class);
		for (TaskModel tm : models)
		{
			System.out.println(tm.getName());
		}
	}

	public TaskModel getTaskModel(String taskName, ProcessModel processModel)
	{
		NodeModel nodeModel = processModel.getNodeModel(taskName);
		Assert.notNull(nodeModel, "任务id无法找到节点模型.");

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
