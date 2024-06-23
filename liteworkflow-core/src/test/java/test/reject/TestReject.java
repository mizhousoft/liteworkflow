package test.reject;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestReject extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		processId = engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("test/reject/reject.xml"));
		engine.getRuntimeService().startInstanceById(processId);
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
