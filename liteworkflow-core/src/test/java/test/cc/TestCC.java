package test.cc;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.order.entity.Order;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestCC extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/task/simple/process.snaker"));
	}

	@Test
	public void test()
	{
		ProcessEngine engine = applicationContext.getBean(ProcessEngine.class);

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		Order order = engine.startInstanceByName("simple", 0, "2", args);
		engine.getOrderService().createCCOrder(order.getId(), "test");
		// engine.getOrderService().updateCCStatus("b0fcc08da45d4e88819d9c287917b525", "test");
		// engine.getOrderService().deleteCCOrder("01b960b9d5df4be7b8565b9f64bc1856", "test");
	}
}
