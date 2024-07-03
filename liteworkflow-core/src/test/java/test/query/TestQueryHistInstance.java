package test.query;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;

import test.TestSpring;

/**
 * 流程实例查询测试
 * 
 * @author
 * @since 1.0
 */
public class TestQueryHistInstance extends TestSpring
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
		HistoricInstancePageRequest request = new HistoricInstancePageRequest();
		request.setStartTime(LocalDateTime.of(2014, 1, 1, 0, 0));
		request.setNames(new String[] { "simple" });
		request.setState(0);
		request.setCategory("预算管理流程1");

		System.out.println(engine.getHistoryService().queryPageData(request));
		System.out.println(engine.getHistoryService().queryPageData(new HistoricInstancePageRequest()));
	}
}
