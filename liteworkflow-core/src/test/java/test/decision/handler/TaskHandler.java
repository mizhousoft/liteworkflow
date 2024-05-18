package test.decision.handler;

import org.snaker.engine.DecisionHandler;
import org.snaker.engine.core.Execution;

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
