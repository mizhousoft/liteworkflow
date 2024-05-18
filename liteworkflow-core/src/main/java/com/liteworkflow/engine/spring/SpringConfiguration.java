package com.liteworkflow.engine.spring;

import org.springframework.context.ApplicationContext;

import com.liteworkflow.engine.SnakerException;
import com.liteworkflow.engine.cfg.Configuration;

/**
 * Spring的配置对象
 * 
 * @author yuqs
 * @since 1.5
 */
public class SpringConfiguration extends Configuration
{
	/**
	 * Spring上下文
	 */
	private ApplicationContext applicationContext;

	public SpringConfiguration(ApplicationContext ctx)
	{
		super(new SpringContext(ctx));
		this.applicationContext = ctx;
	}

	public void parser() throws SnakerException
	{
		super.parser();
	}

	public boolean isCMB()
	{
		return true;
	}

	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}
}
