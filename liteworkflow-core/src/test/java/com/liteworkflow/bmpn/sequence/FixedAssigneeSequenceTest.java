package com.liteworkflow.bmpn.sequence;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * 指定执行人顺序流
 *
 * @version
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
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
		try (InputStream istream = FixedAssigneeSequenceTest.class.getClassLoader().getResourceAsStream("bpmn/leave.bpmn20.xml"))
		{
			repositoryService.deploy(istream);
		}
	}

	@Test
	public void start()
	{
		Map<String, Object> variableMap = new HashMap<String, Object>(1);
		variableMap.put("studentName", "lilin");
		variableMap.put("day", 4);

		ProcessInstance instance = runtimeService.startInstanceByKey("leave", "L1", "peter", variableMap);

		Map<String, Object> variables = new HashMap<>(1);
		variables.put("start", "start");
		variableMap.put("day", 4);

		Task task = taskService.createTaskQuery().queryByInstanceId(instance.getId()).get(0);
		taskService.complete(task.getId(), variables);

		task = taskService.createTaskQuery().queryByInstanceId(instance.getId()).get(0);

		variables.put("outcome", "pass");
		taskService.complete(task.getId(), variables);
	}
}
