package test.surrogate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestSurrogate extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestSurrogate.class.getClassLoader().getResourceAsStream("test/surrogate/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "test" });
		ProcessInstance instance = engine.getRuntimeService().startInstanceByKey("surrogate", null, "2", args);
		System.out.println("instance=" + instance);
	}
}
