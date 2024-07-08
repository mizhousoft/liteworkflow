package test.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestProcess extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestProcess.class.getClassLoader().getResourceAsStream("test/task/simple/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		ProcessDefinition process = engine.getRepositoryService().getProcessDefinition(processId);
		System.out.println("output 1=" + process);
		process = engine.getRepositoryService().getByVersion(process.getKey(), process.getVersion());
		System.out.println("output 2=" + process);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", "1");
		engine.getRuntimeService().startInstanceById(processId, "1", args);
		// engine.startInstanceById(processId, "1", args);
	}
}
