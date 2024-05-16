package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.request.CCOrderPageRequest;

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
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();
	}

	@Test
	public void test()
	{
		CCOrderPageRequest request = new CCOrderPageRequest();
		request.setState(1);

		System.out.println(engine.query().getCCWorks(request));
	}
}
