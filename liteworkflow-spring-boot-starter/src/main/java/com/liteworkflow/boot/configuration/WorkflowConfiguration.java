package com.liteworkflow.boot.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.cfg.EngineConfigurationImpl;

/**
 * WorkflowConfiguration
 *
 * @version
 */
@Configuration
public class WorkflowConfiguration
{
	@Bean
	public ProcessEngine getSnakerEngine(SqlSessionFactory sqlSessionFactory, ApplicationContext applicationContext) throws Exception
	{
		EngineConfigurationImpl cfg = new EngineConfigurationImpl();
		cfg.setSqlSessionFactory(sqlSessionFactory);
		cfg.setApplicationContext(applicationContext);

		return cfg.buildProcessEngine();
	}
}
