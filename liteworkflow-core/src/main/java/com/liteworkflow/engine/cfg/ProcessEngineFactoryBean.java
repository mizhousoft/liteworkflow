package com.liteworkflow.engine.cfg;

import org.springframework.beans.factory.FactoryBean;

import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessEngine;

/**
 * TODO
 *
 * @version
 */
public class ProcessEngineFactoryBean implements FactoryBean<ProcessEngine>
{
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
