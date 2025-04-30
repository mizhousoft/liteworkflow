package com.mizhousoft.liteworkflow.sequence;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mizhousoft.liteworkflow.boot.LiteWorkflowApplication;
import com.mizhousoft.liteworkflow.engine.Authentication;
import com.mizhousoft.liteworkflow.engine.ProcessEngine;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.TaskService;
import com.mizhousoft.liteworkflow.engine.domain.DeployOption;
import com.mizhousoft.liteworkflow.engine.domain.ProcessInstance;
import com.mizhousoft.liteworkflow.engine.domain.Task;

/**
 * 指定执行人顺序流
 *
 * @version
 */
@SpringBootTest(classes = LiteWorkflowApplication.class)
public class FixedAssigneeSequenceTest
{
	@Autowired
	private ProcessEngine processEngine;

	private RepositoryService repositoryService;

	private RuntimeService runtimeService;

	private TaskService taskService;

	@BeforeEach
	public void before() throws IOException
	{
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
	}

	@Test
	public void deploy() throws Exception
	{
		DeployOption deployOption = new DeployOption();

		try (InputStream istream = FixedAssigneeSequenceTest.class.getClassLoader().getResourceAsStream("processes/leave.bpmn20.xml"))
		{
			repositoryService.deploy(istream, "test", deployOption);
		}
	}

	@Test
	public void start()
	{
		Map<String, Object> variableMap = new HashMap<String, Object>(1);
		variableMap.put("studentName", "lilin");
		variableMap.put("day", 4);

		Authentication.setAuthenticatedUserId("peter");
		ProcessInstance instance = runtimeService.startInstanceByKey("leave", "L1", variableMap);

		Map<String, Object> variables = new HashMap<>(1);
		variables.put("start", "start");
		variableMap.put("day", 4);

		Task task = taskService.queryTaskList(instance.getId()).get(0);
		taskService.complete(task.getId(), variables);

		task = taskService.queryTaskList(instance.getId()).get(0);

		variables.put("outcome", "pass");
		taskService.complete(task.getId(), variables);
	}
}
