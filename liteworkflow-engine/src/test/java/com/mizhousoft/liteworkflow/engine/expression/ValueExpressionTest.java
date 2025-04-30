package com.mizhousoft.liteworkflow.engine.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * ValueExpressionTest
 *
 * @version
 */
public class ValueExpressionTest
{
	@Test
	public void test()
	{
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("param", "userA");

		ExpressionParser parser = new SpelExpressionParser();
		String result = parser.parseExpression("#param").getValue(context, String.class);
		Assertions.assertEquals(result, "userA");

		result = parser.parseExpression("#user").getValue(context, String.class);
		Assertions.assertEquals(result, null);
	}
}
