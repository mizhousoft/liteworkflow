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
	private ProcessEngineConfigurationImpl processEngineConfiguration;

	/**
	 * 获取processEngineConfiguration
	 * 
	 * @return
	 */
	public ProcessEngineConfigurationImpl getProcessEngineConfiguration()
	{
		return processEngineConfiguration;
	}

	/**
	 * 设置processEngineConfiguration
	 * 
	 * @param processEngineConfiguration
	 */
	public void setProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration)
	{
		this.processEngineConfiguration = processEngineConfiguration;
	}
}
