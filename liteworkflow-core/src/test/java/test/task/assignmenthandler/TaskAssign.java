package test.task.assignmenthandler;

import com.liteworkflow.engine.Assignment;
import com.liteworkflow.engine.impl.Execution;
import com.liteworkflow.engine.model.TaskModel;

/**
 * @author yuqs
 * @since 1.0
 */
public class TaskAssign extends Assignment
{

	public Object assign(TaskModel model, Execution execution)
	{
		System.out.println(model);
		System.out.println(execution.getArgs());
		return "admin";
	}
}
