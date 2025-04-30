package com.mizhousoft.liteworkflow.engine.cfg;

import org.springframework.beans.factory.FactoryBean;

import com.mizhousoft.liteworkflow.engine.ProcessEngine;
import com.mizhousoft.liteworkflow.engine.ProcessEngineConfiguration;

/**
 * 创建ProcessEngine工程类
 *
 * @version
 */
public class ProcessEngineFactoryBean implements FactoryBean<ProcessEngine>
{
	/**
	 * ProcessEngineConfiguration
	 */
	private ProcessEngineConfiguration engineConfiguration;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessEngine getObject() throws Exception
	{
		return engineConfiguration.buildProcessEngine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType()
	{
		return ProcessEngine.class;
	}

	/**
	 * 设置engineConfiguration
	 * 
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}
}
