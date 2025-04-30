package com.mizhousoft.liteworkflow.engine.impl;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

/**
 * 通用服务
 *
 * @version
 */
public abstract class CommonServiceImpl
{
	/**
	 * ProcessEngineConfigurationImpl
	 */
	protected final ProcessEngineConfigurationImpl engineConfiguration;

	/**
	 * CommandExecutor
	 */
	protected CommandExecutor commandExecutor;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public CommonServiceImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super();
		this.engineConfiguration = engineConfiguration;
	}

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
	 * 获取commandExecutor
	 * 
	 * @return
	 */
	public CommandExecutor getCommandExecutor()
	{
		return commandExecutor;
	}

	/**
	 * 设置commandExecutor
	 * 
	 * @param commandExecutor
	 */
	public void setCommandExecutor(CommandExecutor commandExecutor)
	{
		this.commandExecutor = commandExecutor;
	}
}
