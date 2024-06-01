package test.decision.handler;

import com.liteworkflow.engine.DecisionHandler;
import com.liteworkflow.engine.impl.Execution;

/**
 * @author yuqs
 * @since 1.0
 */
public class TaskHandler implements DecisionHandler
{

	public String decide(Execution execution)
	{
		return (String) execution.getArgs().get("content");
	}

}
