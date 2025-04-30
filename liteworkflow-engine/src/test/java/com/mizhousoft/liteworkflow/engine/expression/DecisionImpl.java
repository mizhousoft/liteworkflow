package com.mizhousoft.liteworkflow.engine.expression;

/**
 * DecisionImpl
 *
 * @version
 */
public class DecisionImpl implements Decision
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String decide(String user)
	{
		if ("userA".equals(user))
		{
			return "ok";
		}
		else
		{
			return "not ok";
		}
	}
}
