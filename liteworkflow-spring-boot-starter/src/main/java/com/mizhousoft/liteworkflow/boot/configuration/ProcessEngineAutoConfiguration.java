package com.mizhousoft.liteworkflow.boot.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.mizhousoft.liteworkflow.engine.HistoryService;
import com.mizhousoft.liteworkflow.engine.ProcessEngine;
import com.mizhousoft.liteworkflow.engine.ProcessInstanceService;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.TaskService;

/**
 * ProcessEngineAutoConfiguration
 *
 * @version
 */
@Configuration
public class ProcessEngineAutoConfiguration
{
	@Autowired
	private ResourcePatternResolver resourcePatternResolver;

	@Autowired
	private FlowProperties flowProperties;

	@Bean
	public SpringProcessEngineConfiguration getProcessEngineConfiguration(SqlSessionFactory sqlSessionFactory,
	        ApplicationContext applicationContext) throws Exception
	{
		SpringProcessEngineConfiguration engineConfiguration = new SpringProcessEngineConfiguration();
		engineConfiguration.setSqlSessionFactory(sqlSessionFactory);
		engineConfiguration.setApplicationContext(applicationContext);

		List<Resource> resources = discoverDeploymentResources();
		engineConfiguration.setDeploymentResources(resources);

		return engineConfiguration;
	}

	@Bean
	public ProcessEngine getProcessEngine(SpringProcessEngineConfiguration engineConfiguration) throws Exception
	{
		return engineConfiguration.buildProcessEngine();
	}

	@Bean
	public RepositoryService getRepositoryService(ProcessEngine processEngine)
	{
		return processEngine.getRepositoryService();
	}

	@Bean
	public RuntimeService getRuntimeService(ProcessEngine processEngine)
	{
		return processEngine.getRuntimeService();
	}

	@Bean
	public TaskService getTaskService(ProcessEngine processEngine)
	{
		return processEngine.getTaskService();
	}

	@Bean
	public ProcessInstanceService getProcessInstanceService(ProcessEngine processEngine)
	{
		return processEngine.getProcessInstanceService();
	}

	@Bean
	public HistoryService getHistoryService(ProcessEngine processEngine)
	{
		return processEngine.getHistoryService();
	}

	private List<Resource> discoverDeploymentResources() throws IOException
	{
		if (!flowProperties.isCheckProcessDefinitions())
		{
			return new ArrayList<>(0);
		}

		String prefix = flowProperties.getProcessDefinitionLocationPrefix();
		List<String> suffixes = flowProperties.getProcessDefinitionLocationSuffixes();
		if (prefix == null || null == suffixes)
		{
			return null;
		}

		List<Resource> results = new ArrayList<>();
		for (String suffix : suffixes)
		{
			Resource[] resources = resourcePatternResolver.getResources(prefix + suffix);
			if (resources != null && resources.length > 0)
			{
				Collections.addAll(results, resources);
			}
		}

		return results;
	}
}
