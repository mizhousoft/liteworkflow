package com.mizhousoft.liteworkflow.engine.sequence;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class InitiatorTest
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

		try (InputStream istream = InitiatorTest.class.getClassLoader().getResourceAsStream("processes/leave-initiator.bpmn20.xml"))
		{
			repositoryService.deploy(istream, "test", deployOption);
		}
	}

	@Test
	public void start() throws Exception
	{
		Map<String, Object> variableMap = new HashMap<String, Object>(1);
		variableMap.put("studentName", "lilin");
		variableMap.put("day", 4);

		Authentication.setAuthenticatedUserId("joy");
		ProcessInstance instance = runtimeService.startInstanceByKey("leave-initiator", "L1", variableMap);

		Map<String, Object> variables = new HashMap<>(1);
		variables.put("start", "start");
		variableMap.put("day", 4);

		Task task = taskService.queryTaskList(instance.getId()).get(0);
		taskService.complete(task.getId(), variables);

		TimeUnit.SECONDS.sleep(2);

		task = taskService.queryTaskList(instance.getId()).get(0);

		variables.put("outcome", "pass");
		taskService.complete(task.getId(), variables);
	}
}
