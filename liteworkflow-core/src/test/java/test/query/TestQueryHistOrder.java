package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.order.request.HistoryOrderPageRequest;

import test.TestSpring;

/**
 * 流程实例查询测试
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestQueryHistOrder extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();
	}

	@Test
	public void test()
	{
		HistoryOrderPageRequest request = new HistoryOrderPageRequest();
		request.setCreateTimeStart("2014-01-01");
		request.setNames(new String[] { "simple" });
		request.setState(0);
		request.setProcessType("预算管理流程1");

		System.out.println(engine.query().getHistoryOrders(request));
		System.out.println(engine.query().getHistoryOrders(new HistoryOrderPageRequest()));
	}
}
