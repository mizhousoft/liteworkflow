package com.liteworkflow.boot.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

/**
 * WorkflowConfiguration
 *
 * @version
 */
@Configuration
public class ProcessEngineAutoConfiguration
{
	@Bean
	public ProcessEngine getSnakerEngine(SqlSessionFactory sqlSessionFactory, ApplicationContext applicationContext) throws Exception
	{
		ProcessEngineConfigurationImpl cfg = new ProcessEngineConfigurationImpl();
		cfg.setSqlSessionFactory(sqlSessionFactory);
		cfg.setApplicationContext(applicationContext);

		return cfg.buildProcessEngine();
	}
}
