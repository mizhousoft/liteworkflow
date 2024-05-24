package test.process;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.process.entity.Process;

import test.TestSpring;

/**
 * @author yuqs
 * @since 1.0
 */
public class TestProcess extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		processService = engine.getProcessService();

		processId = engine.getProcessService().deploy(StreamHelper.getStreamFromClasspath("test/task/simple/process.snaker"));
	}

	@Test
	public void test()
	{
		Process process = engine.getProcessService().getProcessById(processId);
		System.out.println("output 1=" + process);
		process = engine.getProcessService().getProcessByVersion(process.getName(), process.getVersion());
		System.out.println("output 2=" + process);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("task1.operator", "1");
		engine.startInstanceById(processId, "1", args);
		engine.getProcessService().undeploy(processId);
		// engine.startInstanceById(processId, "1", args);
	}
}
