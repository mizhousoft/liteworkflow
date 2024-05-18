package test.custom;

import com.liteworkflow.engine.core.Execution;
import com.liteworkflow.engine.handlers.IHandler;

/**
 * @author yuqs
 * @since 1.0
 */
public class CustomHandler implements IHandler
{

	public void handle(Execution execution)
	{
		System.out.println("custom handler");
	}
}
