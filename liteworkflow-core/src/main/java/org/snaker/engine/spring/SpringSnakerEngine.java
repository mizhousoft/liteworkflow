package org.snaker.engine.spring;

import org.snaker.engine.core.SnakerEngineImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Properties;

/**
 * spring环境使用的SnakerEngine实现类，主要接收spring的applicationContext对象
 * 
 * @author yuqs
 * @since 1.0
 */
public class SpringSnakerEngine extends SnakerEngineImpl implements InitializingBean, ApplicationContextAware
{
	private ApplicationContext applicationContext;

	private Properties properties;

	public void afterPropertiesSet() throws Exception
	{
		SpringConfiguration configuration = new SpringConfiguration(applicationContext);
		if (properties != null)
			configuration.initProperties(properties);
		configuration.parser();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}

	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}
}
