package com.mizhousoft.liteworkflow.engine.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * BeanExpressionTest
 *
 * @version
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:expression/applicationContext.xml" })
public class BeanExpressionTest
{
	/**
	 * Spring上下文
	 */
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test()
	{
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(applicationContext));
		context.setVariable("param", "userA");

		ExpressionParser parser = new SpelExpressionParser();
		String result = parser.parseExpression("@decision.decide(#param)").getValue(context, String.class);
		Assertions.assertEquals(result, "ok");

		context.setVariable("param", "userB");
		result = parser.parseExpression("@decision.decide(#param)").getValue(context, String.class);
		Assertions.assertEquals(result, "not ok");

		Decision decision = parser.parseExpression("@decision").getValue(context, Decision.class);
		Assertions.assertNotNull(decision);
	}
}
