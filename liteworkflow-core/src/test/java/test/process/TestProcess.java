package test.process;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestProcess extends TestSpring
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
		ProcessDefinition process = engine.getRepositoryService().getProcessById(processId);
		System.out.println("output 1=" + process);
		process = engine.getRepositoryService().getProcessByVersion(process.getName(), process.getVersion());
		System.out.println("output 2=" + process);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", "1");
		engine.getRuntimeService().startInstanceById(processId, "1", args);
		engine.getRepositoryService().undeploy(processId);
		// engine.startInstanceById(processId, "1", args);
	}
}
