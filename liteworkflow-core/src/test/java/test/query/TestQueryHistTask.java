package test.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.task.request.TaskPageRequest;
import com.liteworkflow.engine.persistence.workitem.request.WorkItemPageRequest;

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
		repositoryService = engine.getRepositoryService();
	}

	@Test
	public void test()
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setOperators(new String[] { "admin" });
		System.out.println(engine.getHistoryService().getHistoryTasks(request));

		WorkItemPageRequest wRequest = new WorkItemPageRequest();
		wRequest.setOperators(new String[] { "admin" });
		System.out.println(engine.getHistoryService().getHistoryWorkItems(wRequest));
	}
}
