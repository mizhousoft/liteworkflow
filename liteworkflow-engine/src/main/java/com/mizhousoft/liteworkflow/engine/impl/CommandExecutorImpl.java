package com.mizhousoft.liteworkflow.engine.impl;

import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

/**
 * 命令执行器
 *
 * @version
 */
public class CommandExecutorImpl implements CommandExecutor
{
	/**
	 * ProcessEngineConfigurationImpl
	 */
	private ProcessEngineConfigurationImpl engineConfiguration;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 */
	public CommandExecutorImpl(ProcessEngineConfigurationImpl engineConfiguration)
	{
		super();
		this.engineConfiguration = engineConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T execute(Command<T> command)
	{
		CommandContext context = new CommandContext();
		context.setEngineConfiguration(engineConfiguration);

		return command.execute(context);
	}
}
