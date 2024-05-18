package test.custom;

import org.snaker.engine.core.Execution;
import org.snaker.engine.handlers.IHandler;

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
