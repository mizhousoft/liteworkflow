package com.mizhousoft.liteworkflow.engine.impl.el;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.mizhousoft.liteworkflow.engine.impl.Expression;

/**
 * Spring el表达式解析器
 * 
 * @author
 * @since 1.0
 */
public class SpelExpression implements Expression
{
	/**
	 * 表达式解析器
	 */
	private ExpressionParser parser = new SpelExpressionParser();

	/**
	 * ApplicationContext
	 */
	private ApplicationContext applicationContext;

	/**
	 * 构造函数
	 *
	 * @param applicationContext
	 */
	public SpelExpression(ApplicationContext applicationContext)
	{
		super();
		this.applicationContext = applicationContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T eval(Class<T> T, String expr, Map<String, Object> variableMap)
	{
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(applicationContext));
		context.setVariables(variableMap);

		return parser.parseExpression(expr).getValue(context, T);
	}
}
