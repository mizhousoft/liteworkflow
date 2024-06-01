package test.custom;

import com.liteworkflow.engine.IHandler;
import com.liteworkflow.engine.impl.Execution;

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
