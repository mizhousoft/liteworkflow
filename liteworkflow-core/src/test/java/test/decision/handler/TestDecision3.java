package test.decision.handler;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;

import test.TestSpring;

/**
 * 测试决策分支流程2：使用transition的expr属性决定后置路线
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestDecision3 extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/decision/handler/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		args.put("task2.operator", new String[] { "1" });
		args.put("task3.operator", new String[] { "1" });
		args.put("content", "toTask3");
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println(order);
	}
}
