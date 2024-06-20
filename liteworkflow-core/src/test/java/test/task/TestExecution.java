package test.task;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestExecution extends TestSpring
{
	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		// engine.executeTask("ad0857146f6145549c36c1264474b8e1", "1", args);
	}
}
