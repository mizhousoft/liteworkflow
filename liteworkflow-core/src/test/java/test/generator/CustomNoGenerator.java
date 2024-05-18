package test.generator;

import org.snaker.engine.NoGenerator;
import org.snaker.engine.model.ProcessModel;

/**
 * @author yuqs
 * @since 1.0
 */
public class CustomNoGenerator implements NoGenerator
{

	public String generate(ProcessModel model)
	{
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

}
