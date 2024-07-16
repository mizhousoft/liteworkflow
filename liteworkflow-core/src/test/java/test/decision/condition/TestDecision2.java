package test.decision.condition;

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
 * 测试决策分支流程2：使用transition的expr属性决定后置路线
 * 
 * @author
 * @since 1.0
 */
public class TestDecision2 extends TestSpring
{
	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestDecision2.class.getClassLoader().getResourceAsStream("test/decision/condition/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		args.put("task2.operator", new String[] { "1" });
		args.put("task3.operator", new String[] { "1" });
		args.put("content", 250);
		ProcessInstance instance = engine.getRuntimeService().startInstanceById(processId, null, "2", args);
		System.out.println(instance);
	}
}
