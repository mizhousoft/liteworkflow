package test.time.expire;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

import test.TestSpring;

/**
 * @author
 * @since 1.0
 */
public class TestExpire extends TestSpring
{
	private static final String PROCESSNAME = "expire";

	@BeforeEach
	public void before() throws IOException
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();

		try (InputStream istream = TestExpire.class.getClassLoader().getResourceAsStream("test/time/expire/process.xml"))
		{
			engine.getRepositoryService().deploy(istream);
		}
	}

	@Test
	public void test()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", new String[] { "1" });
		args.put("task1.expireTime", LocalDateTimeUtils.toDate(LocalDateTime.of(2014, 4, 15, 9, 0)));
		args.put("task1.reminderTime", LocalDateTimeUtils.toDate(LocalDateTime.of(2014, 4, 15, 8, 57)));
		ProcessInstance instance = engine.getRuntimeService().startInstanceByKey(PROCESSNAME, null, "2", args);
		System.out.println("instance=" + instance);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{

		}
	}
}
