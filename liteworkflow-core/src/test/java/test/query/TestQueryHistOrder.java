package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.request.HistoricProcessInstPageRequest;

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
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();
	}

	@Test
	public void test()
	{
		HistoricProcessInstPageRequest request = new HistoricProcessInstPageRequest();
		request.setCreateTimeStart("2014-01-01");
		request.setNames(new String[] { "simple" });
		request.setState(0);
		request.setProcessType("预算管理流程1");

		System.out.println(engine.getHistoryService().getHistoryOrders(request));
		System.out.println(engine.getHistoryService().getHistoryOrders(new HistoricProcessInstPageRequest()));
	}
}
