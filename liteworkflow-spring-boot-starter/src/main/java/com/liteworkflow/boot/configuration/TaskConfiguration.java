package com.liteworkflow.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liteworkflow.process.mapper.ProcessMapper;
import com.liteworkflow.process.service.ProcessEntityService;
import com.liteworkflow.process.service.impl.ProcessEntityServiceImpl;

/**
 * TaskConfiguration
 *
 * @version
 */
@Configuration
public class TaskConfiguration
{
	@Bean
	public ProcessEntityService getProcessEntityService(ProcessMapper processMapper)
	{
		ProcessEntityServiceImpl processEntityService = new ProcessEntityServiceImpl();
		processEntityService.setProcessMapper(processMapper);

		return processEntityService;
	}
}
