package test.task.group;

import java.util.ArrayList;
import java.util.List;

import org.snaker.engine.impl.GeneralAccessStrategy;

/**
 * @author yuqs
 * @since 1.0
 */
public class CustomAccessStrategy extends GeneralAccessStrategy
{
	@Override
	protected List<String> ensureGroup(String operator)
	{
		List<String> groups = new ArrayList<String>();
		if (operator.equals("test"))
		{
			groups.add("test");
		}
		else
		{
			groups.add("role1");
		}
		return groups;
	}
}
