package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.request.ProcessPageRequest;

import test.TestSpring;

/**
 * 流程定义查询测试
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestQueryProcess extends TestSpring
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
		ProcessPageRequest request = new ProcessPageRequest();

		System.out.println(engine.process().getProcesss(request));

		request.setNames(new String[] { "subprocess1" });
		System.out.println(engine.process().getProcesss(request));

		System.out.println(engine.process().getProcessByVersion("subprocess1", 0));
		System.out.println(engine.process().getProcessByName("subprocess1"));
	}
}
