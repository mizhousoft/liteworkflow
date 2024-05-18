package test.task.assignmenthandler;

import org.snaker.engine.Assignment;
import org.snaker.engine.core.Execution;
import org.snaker.engine.model.TaskModel;

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
