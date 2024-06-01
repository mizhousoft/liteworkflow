package test.generator;

import com.liteworkflow.engine.impl.NoGenerator;
import com.liteworkflow.engine.model.ProcessModel;

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
