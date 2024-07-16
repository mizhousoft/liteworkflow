package com.liteworkflow.engine.impl.command;

import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * TODO
 *
 * @version
 */
public class SetInstanceOwnerCommand implements Command<ProcessInstance>
{
	private int instanceId;

	private String owner;

	/**
	 * 构造函数
	 *
	 * @param instanceId
	 * @param owner
	 */
	public SetInstanceOwnerCommand(int instanceId, String owner)
	{
		super();
		this.instanceId = instanceId;
		this.owner = owner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstance execute(CommandContext context)
	{
		return null;
	}
}
