package test.reject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestReject extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestReject.class.getClassLoader().getResourceAsStream("test/reject/reject.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}

		engine.getRuntimeService().startInstanceById(processId, null);
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("number", 2);
		// engine.executeTask("f4a7a9b486ca41d3a2ebb1ecc0af75a9", null, args);
		// engine.executeAndJumpTask("737a9d4118594d69a918ed20daf347cb", null, args, "task1");
	}
}
