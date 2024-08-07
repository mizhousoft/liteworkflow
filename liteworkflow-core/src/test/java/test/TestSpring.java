package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.RepositoryService;

/**
 * @author
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSpring extends AbstractJUnit4SpringContextTests
{
	protected int processId;

	protected ProcessEngine engine;

	protected RepositoryService repositoryService;

	@Test
	public void test()
	{

	}
}
