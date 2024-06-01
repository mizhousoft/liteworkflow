package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.order.request.CCOrderPageRequest;

import test.TestSpring;

/**
 * 流程实例查询测试
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestQueryCCOrder extends TestSpring
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
		CCOrderPageRequest request = new CCOrderPageRequest();
		request.setState(1);

		System.out.println(engine.getHistoryService().getCCWorks(request));
	}
}
