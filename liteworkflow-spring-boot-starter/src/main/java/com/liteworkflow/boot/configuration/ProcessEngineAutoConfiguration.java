package com.liteworkflow.boot.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

/**
 * ProcessEngineAutoConfiguration
 *
 * @version
 */
@Configuration
public class ProcessEngineAutoConfiguration
{
	@Bean
	public ProcessEngine getProcessEngine(SqlSessionFactory sqlSessionFactory, ApplicationContext applicationContext) throws Exception
	{
		ProcessEngineConfigurationImpl engineConfiguration = new ProcessEngineConfigurationImpl();
		engineConfiguration.setSqlSessionFactory(sqlSessionFactory);
		engineConfiguration.setApplicationContext(applicationContext);

		return engineConfiguration.buildProcessEngine();
	}
}
