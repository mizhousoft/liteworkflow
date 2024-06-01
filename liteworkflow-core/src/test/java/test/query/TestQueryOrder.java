package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.order.request.ProcessInstPageRequest;

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
		repositoryService = engine.getRepositoryService();
	}

	@Test
	public void test()
	{
		ProcessInstPageRequest request = new ProcessInstPageRequest();
		request.setCreateTimeStart("2014-01-01");
		request.setProcessId("860e5edae536495a9f51937f435a1c01");

		System.out.println(engine.getProcessInstanceService().getActiveOrders(request));

		System.out.println(engine.getProcessInstanceService().getActiveOrders(new ProcessInstPageRequest()));
		System.out.println(engine.getProcessInstanceService().getOrder("b2802224d75d4847ae5bfb0f7e621b8f"));
	}
}
