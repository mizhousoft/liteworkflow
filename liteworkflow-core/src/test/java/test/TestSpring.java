package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.RepositoryService;

/**
 * @author yuqs
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-snaker.xml" })
public class TestSpring extends AbstractJUnit4SpringContextTests
{
	protected String processId;

	protected ProcessEngine engine;

	protected RepositoryService repositoryService;

	@Test
	public void test()
	{
		// SnakerEngine engine = applicationContext.getBean(SnakerEngine.class);
		// engine.getRepositoryService().deploy(StreamHelper.getStreamFromClasspath("contactform.snaker"));
		// Map<String, Object> args = new HashMap<String, Object>();
		// args.put("receipt.operator", new String[]{"1"});
		// Order order = engine.startInstanceByName("contactFormFlow", 0, "2", args);
		// System.out.println("order=" + order);
		// List<Task> tasks = engine.getQueryService().getActiveTasks(new
		// QueryFilter().setOrderId(order.getId()));
		// for(Task task : tasks) {
		// engine.executeTask(task.getId(), "1", args);
		// }
	}
}
