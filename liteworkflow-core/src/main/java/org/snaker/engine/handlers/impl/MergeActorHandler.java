package org.snaker.engine.handlers.impl;

/**
 * actor all方式的合并处理器
 * 
 * @author yuqs
 * @since 1.0
 */
public class MergeActorHandler extends AbstractMergeHandler
{
	/**
	 * 调用者需要提供actor all的任务名称
	 */
	private String taskName;

	/**
	 * 构造函数，由调用者提供taskName
	 * 
	 * @param taskName
	 */
	public MergeActorHandler(String taskName)
	{
		this.taskName = taskName;
	}

	/**
	 * actor all方式，查询参数为：orderId、taskName
	 * 
	 * @see org.snaker.engine.handlers.impl.AbstractMergeHandler#findActiveNodes()
	 */
	protected String[] findActiveNodes()
	{
		return new String[] { taskName };
	}
}
