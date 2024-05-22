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
public class TestQueryTask extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.process();
		queryService = engine.query();
	}

	@Test
	public void test()
	{
		TaskPageRequest request = new TaskPageRequest();
		request.setOperators(new String[] { "1" });
		System.out.println(queryService.getActiveTasks(request));

		WorkItemPageRequest rrequest = new WorkItemPageRequest();
		rrequest.setOperators(new String[] { "1" });
		rrequest.setOrderId("36c0228fcfa740d5b62682dc954eaecd");

		System.out.println(queryService.getWorkItems(rrequest));
	}
}
