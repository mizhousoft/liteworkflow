package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.task.request.TaskPageRequest;
import com.liteworkflow.workitem.request.WorkItemPageRequest;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestQueryHistTask extends TestSpring
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
		TaskPageRequest request = new TaskPageRequest();
		request.setOperators(new String[] { "admin" });
		System.out.println(queryService.getHistoryTasks(request));

		WorkItemPageRequest wRequest = new WorkItemPageRequest();
		wRequest.setOperators(new String[] { "admin" });
		System.out.println(queryService.getHistoryWorkItems(wRequest));
	}
}
