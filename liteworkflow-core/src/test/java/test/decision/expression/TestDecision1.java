package test.decision.expression;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;

import test.TestSpring;

/**
 * 测试决策分支流程1：决策节点decision使用expr属性决定后置路线
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestDecision1 extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/decision/expression/process.snaker"));
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		// args.put("task1.operator", new String[]{"1","2"});
		args.put("task2.operator", new String[] { "1" });
		// args.put("task3.operator", new String[]{"1","2"});
		args.put("content", "toTask2");
		Order order = engine.startInstanceById(processId, "2", args);
		System.out.println(order);
	}
}
