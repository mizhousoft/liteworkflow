package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.order.request.OrderPageRequest;

import test.TestSpring;

/**
 * 流程实例查询测试
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestQueryOrder extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();
	}

	@Test
	public void test()
	{
		OrderPageRequest request = new OrderPageRequest();
		request.setCreateTimeStart("2014-01-01");
		request.setProcessId("860e5edae536495a9f51937f435a1c01");

		System.out.println(engine.getQueryService().getActiveOrders(request));

		System.out.println(engine.getQueryService().getActiveOrders(new OrderPageRequest()));
		System.out.println(engine.getQueryService().getOrder("b2802224d75d4847ae5bfb0f7e621b8f"));
	}
}
