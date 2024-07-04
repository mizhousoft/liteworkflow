package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.request.TaskPageRequest;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestQueryTask extends TestSpring
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
		TaskPageRequest request = new TaskPageRequest();
		request.setOperators(new String[] { "1" });
		System.out.println(engine.getTaskService().queryPageData(request));
	}
}
