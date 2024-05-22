package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
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
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();
		queryService = engine.getQueryService();
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
