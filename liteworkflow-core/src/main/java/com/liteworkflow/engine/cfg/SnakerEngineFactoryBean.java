package com.liteworkflow.engine.cfg;

import org.springframework.beans.factory.FactoryBean;

import com.liteworkflow.engine.EngineConfiguration;
import com.liteworkflow.engine.ProcessEngine;

/**
 * TODO
 *
 * @version
 */
public class SnakerEngineFactoryBean implements FactoryBean<ProcessEngine>
{
	private EngineConfiguration engineConfiguration;

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
	public void setEngineConfiguration(EngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}
}
