package test.query;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.process.entity.Process;
import com.liteworkflow.process.request.ProcessPageRequest;

import test.TestSpring;

/**
 * 流程定义查询测试
 * 
 * @author yuqs
 * @since 1.0
 */
public class TestQueryProcess extends TestSpring
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
		ProcessPageRequest request = new ProcessPageRequest();

		List<Process> list = engine.process().getProcesss(request);
		System.out.println(list.size());

		request.setNames(new String[] { "subprocess1" });
		list = engine.process().getProcesss(request);
		System.out.println(list.size());

		Process process = engine.process().getProcessByVersion("subprocess1", 0);
		System.out.println(process);

		process = engine.process().getProcessByName("subprocess1");
		System.out.println(process);
	}
}
