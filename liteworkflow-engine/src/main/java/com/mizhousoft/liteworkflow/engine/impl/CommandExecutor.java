package com.mizhousoft.liteworkflow.engine.impl;

/**
 * 命令执行器
 *
 * @version
 */
public interface CommandExecutor
{
	/**
	 * 执行命令
	 * 
	 * @param <T>
	 * @param command
	 * @return
	 */
	<T> T execute(Command<T> command);
}
