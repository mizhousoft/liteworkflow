package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

/**
 * 命令上下文
 *
 * @version
 */
public class CommandContext
{
	/**
	 * ProcessEngineConfigurationImpl
	 */
	private ProcessEngineConfigurationImpl engineConfiguration;

	/**
	 * 获取engineConfiguration
	 * 
	 * @return
	 */
	public ProcessEngineConfigurationImpl getEngineConfiguration()
	{
		return engineConfiguration;
	}

	/**
	 * 设置engineConfiguration
	 * 
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfigurationImpl engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}
}
