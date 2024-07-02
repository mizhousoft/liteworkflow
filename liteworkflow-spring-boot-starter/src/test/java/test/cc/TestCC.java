package test.cc;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.liteworkflow.boot.WorkflowApplication;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.RepositoryService;

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

	}
}
