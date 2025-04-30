package com.mizhousoft.liteworkflow.engine.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * OperatorExpressionTest
 *
 * @version
 */
public class OperatorExpressionTest
{
	@Test
	public void test()
	{
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("param", 3);
		context.setVariable("user", "Jack");

		ExpressionParser parser = new SpelExpressionParser();
		boolean result = parser.parseExpression("#param > 3").getValue(context, Boolean.class);
		Assertions.assertFalse(result);

		result = parser.parseExpression("#user.equals('Mack')").getValue(context, Boolean.class);
		Assertions.assertFalse(result);
	}
}
