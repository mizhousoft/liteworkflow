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
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.UserTaskModel;
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
		ProcessInstance instance = engine.getRuntimeService().startInstanceByKey("simple", null, "2", args);
		System.out.println("instance=" + instance);

		ProcessDefinition process = engine.getRepositoryService().getProcessDefinition(instance.getProcessDefinitionId());
		BpmnModel bpmnModel = process.getBpmnModel();

		List<Task> tasks = engine.getTaskService().createTaskQuery().queryByInstanceId(instance.getId());
		for (Task task : tasks)
		{
			UserTaskModel model = getTaskModel(task.getTaskDefinitionId(), bpmnModel);
			System.out.println(model.getId());
		}
	}

	public UserTaskModel getTaskModel(String taskName, BpmnModel bpmnModel)
	{
		FlowNode nodeModel = bpmnModel.getFlowNodeModel(taskName);
		Assert.notNull(nodeModel, "任务id无法找到节点模型.");

		if (nodeModel instanceof UserTaskModel)
		{
			return (UserTaskModel) nodeModel;
		}
		else
		{
			throw new IllegalArgumentException("任务id找到的节点模型不匹配");
		}
	}

}
