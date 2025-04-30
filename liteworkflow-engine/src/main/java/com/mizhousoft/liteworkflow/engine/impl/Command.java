package com.mizhousoft.liteworkflow.engine.impl;

/**
 * 命令
 *
 * @version
 */
public interface Command<T>
{
	/**
	 * 执行命令
	 * 
	 * @param context
	 * @return
	 */
	T execute(CommandContext context);
}
