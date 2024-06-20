package test.cc;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestCC extends TestSpring
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
		ProcessEngine engine = applicationContext.getBean(ProcessEngine.class);

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceByName("simple", 0, "2", args);
		engine.getProcessInstanceService().createCCInstance(instance.getId(), "test");
	}
}
