package test.task.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.SnakerEngine;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.TaskModel;

import test.TestSpring;

/**
 * @author yuqs
 */
public class TestField extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(SnakerEngine.class);
		processService = engine.process();
		queryService = engine.query();

		processId = engine.process().deploy(StreamHelper.getStreamFromClasspath("test/task/field/process.snaker"));
	}

	@Test
	public void test()
	{
		ProcessModel model = engine.process().getProcessById(processId).getModel();
		TaskModel taskModel = (TaskModel) model.getNode("task1");
		System.out.println(taskModel.getFields());
	}
}
