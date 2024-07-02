package com.liteworkflow.engine.impl;

import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;

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
	private ProcessEngineConfigurationImpl processEngineConfiguration;

	/**
	 * 构造函数
	 *
	 * @param processEngineConfiguration
	 */
	public CommandExecutorImpl(ProcessEngineConfigurationImpl processEngineConfiguration)
	{
		super();
		this.processEngineConfiguration = processEngineConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T execute(Command<T> command)
	{
		CommandContext commandContext = new CommandContext();
		commandContext.setProcessEngineConfiguration(processEngineConfiguration);

		return command.execute(commandContext);
	}
}
