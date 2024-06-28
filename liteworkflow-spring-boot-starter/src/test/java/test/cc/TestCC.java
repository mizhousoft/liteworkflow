package test.cc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.liteworkflow.boot.WorkflowApplication;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * @author yuqs
 * @since 1.0
 */
@SpringBootTest(classes = WorkflowApplication.class)
public class TestCC
{
	protected String processId;

	@Autowired
	protected ProcessEngine engine;

	protected RepositoryService repositoryService;

	@BeforeEach
	public void before() throws IOException
	{
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestCC.class.getClassLoader().getResourceAsStream("test/task/simple/process.xml"))
		{
			processId = engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		ProcessInstance order = engine.getRuntimeService().startInstanceByName("simple", 0, "2", args);
		engine.getProcessInstanceService().createCCInstance(order.getId(), "test");
		// engine.getOrderService().updateCCStatus("b0fcc08da45d4e88819d9c287917b525", "test");
		// engine.getOrderService().deleteCCOrder("01b960b9d5df4be7b8565b9f64bc1856", "test");
	}
}
